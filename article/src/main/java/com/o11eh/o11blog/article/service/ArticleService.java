package com.o11eh.o11blog.article.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.o11eh.o11blog.article.repository.ArticleRepository;
import com.o11eh.o11blog.article.repository.projection.ArticleBrief;
import com.o11eh.o11blog.servicebase.config.BusinessException;
import com.o11eh.o11blog.servicebase.config.RedisConfig;
import com.o11eh.o11blog.servicebase.constants.RabbitConstants;
import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import com.o11eh.o11blog.servicebase.entity.PageReq;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import com.o11eh.o11blog.servicebase.entity.front.Member;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleReq;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleVo;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
@ConfigurationProperties("article")
public class ArticleService {

    private final RedisTemplate<String, Object> redis;
    static final String SOURCE = "原创";
    private final ArticleRepository articleRepository;
    private List<Integer> recommendNumber;
    private final RabbitTemplate rabbit;

    public void create(ArticleVo articleVo) {
        Article article = new Article();
        BeanUtil.copyProperties(articleVo, article, CopyOptions.create().ignoreError().setIgnoreProperties("id"));
        String memberId = StpUtil.getLoginIdAsString();
        Member member = new Member();
        member.setId(memberId);
        article.setMember(member);

        article.idToCategories(articleVo.getCategories())
                .idToTags(articleVo.getTags()).setNotPublish();

        // 原创文章，且没有来源，则设置来源信息
        if (article.isOriginal() && article.getSource() == null) {
            article.setSource(SOURCE);
        }

        articleRepository.save(article);
    }

    public void update(ArticleVo articleVo) {
        String memberId = StpUtil.getLoginIdAsString();
        Article article = articleRepository.findByIdAndMemberId(articleVo.getId(), memberId);

        if (article == null) {
            throw BusinessException.e("无法修改该文章");
        }

        BeanUtil.copyProperties(articleVo, article, CopyOptions.create().ignoreError());
        article.setNotPublish();
        articleRepository.save(article);
    }

    public void updateArticleStatus(List<String> ids, ArticleStatus status) {
        List<Article> articles = ids.stream().map(id -> {
            Article article = new Article();
            article.setId(id);
            article.setStatus(status);
            return article;
        }).collect(Collectors.toList());
        for (Article article : articles) {
            articleRepository.updateById(article);
        }
    }

    public List<Article> getRecommendArticle(int level) {
        String key = "recommend:level" + level;

        List<Article> cache = (List<Article>) redis.opsForValue().get(key);

        if (cache != null) {
            return cache;
        }

        int number = recommendNumber.get(level);
        List<Article> articles = articleRepository.getRecommendArticle(level, number);

        if (number > articles.size()) {
            Page<Article> others = articleRepository.findAll((root, query, criteriaBuilder) -> query.where(criteriaBuilder.equal(root.get("status"), ArticleStatus.PUBLISHED),
                            criteriaBuilder.not(root.get("id").in(articles.stream().map(BaseEntry::getId).collect(Collectors.toList())))).getRestriction(),
                    PageRequest.of(0, number - articles.size(), Sort.Direction.DESC, "viewCount"));
            articles.addAll(others.getContent());
        }

        List<Article> toRedis = BeanUtil.copyToList(articles, Article.class);
        redis.opsForValue().set(key, toRedis, 30, TimeUnit.MINUTES);

        return articles;
    }

    public List<Article> getPersonalArticle(String memberId) {
        Member member = new Member();
        member.setId(memberId);
        return articleRepository.findByMember(member);
    }

    public Page<ArticleBrief> getArticleBriefPage(ArticleReq req) {

        Page<ArticleBrief> page = null;
        Sort sort = Sort.by(Sort.Direction.DESC, "view_count");
        switch (req.getType()) {
            case LATEST:
                sort = Sort.by(Sort.Direction.DESC, "create_time");
                page = articleRepository.getArticleBriefPage(PageRequest.of(req.getPage(), req.getSize(), sort));
                break;
            case HOT:
                page = articleRepository.getArticleBriefPage(PageRequest.of(req.getPage(), req.getSize(), sort));
                break;
            case CATEGORY:
                page = articleRepository.getArticlePageByCategory(req.getCategoryId(), PageRequest.of(req.getPage(), req.getSize(), sort));
        }

        return page;
    }

    public Article getArticle(String id) {
        Optional<Article> result = articleRepository.findOne((root, query, criteriaBuilder) -> query.where(criteriaBuilder.equal(root.get("id"), id),
                criteriaBuilder.equal(root.get("status"), ArticleStatus.PUBLISHED)).getRestriction());

        if (!result.isPresent()) {
            return null;
        }
        Article article = result.get();

        redis.executePipelined((RedisCallback<List<Object>>) connection -> {
            connection.openPipeline();
            String viewKey = RedisConfig.ARTICLE_VIEW + id;
            if (true) {
                String key = viewKey;
                Long viewCount = redis.opsForValue().increment(key);

                article.setViewCount(viewCount);
            }
            byte[] bytes = connection.get(viewKey.getBytes(StandardCharsets.UTF_8));
            if (bytes != null) {
                long viewCount = Long.parseLong(new String(bytes));
                article.setViewCount(viewCount);
            }

            byte[] memberId = (StpUtil.getLoginIdAsString()).getBytes(StandardCharsets.UTF_8);
            byte[] likeKey = (RedisConfig.ARTICLE_LIKE + id).getBytes(StandardCharsets.UTF_8);
            connection.setCommands().sIsMember(likeKey, memberId);
            connection.setCommands().sCard(likeKey);
            List<Object> results = connection.closePipeline();
            article.setLiked((Boolean) results.get(1));
            article.setLikeCount((Long) results.get(2));
            return null;
        });
        return article;
    }

    public void likeAction(String aid, String action) {
        String mid = StpUtil.getLoginIdAsString();
        String key = RedisConfig.ARTICLE_LIKE + aid;

        Article article = new Article();
        article.setId(aid);

        if (action.equals("like")) {
            redis.opsForSet().add(key, mid);
            article.setLiked(true);
        } else if (action.equals("dislike")) {
            redis.opsForSet().remove(key, mid);
            article.setLiked(false);
        }
    }

    public Page<Article> getArticlePage(PageReq pageReq) {
        Page<Article> page = articleRepository.findAll(PageRequest.of(Math.toIntExact(pageReq.getCurrent()), Math.toIntExact(pageReq.getSize())));
        return page;
    }

    public void audit(List<String> articleIds) {
        updateArticleStatus(articleIds, ArticleStatus.PUBLISHED);
        articleSync(articleIds);
    }

    @Async
    void articleSync(List<String> articleIds) {
        List<Article> articles = articleRepository.findAllById(articleIds);
        List<Article> results = BeanUtil.copyToList(articles, Article.class);
        for (Article article : results) {
            rabbit.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.ROUTING_KEY_ARTICLE, article);
        }
    }
}
