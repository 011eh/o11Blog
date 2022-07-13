package com.o11eh.o11blog.article.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.o11eh.o11blog.article.repository.ArticleRepository;
import com.o11eh.o11blog.article.repository.projection.ArticleBrief;
import com.o11eh.o11blog.servicebase.config.BusinessException;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import com.o11eh.o11blog.servicebase.entity.front.Member;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleReq;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleVo;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@AllArgsConstructor
@ConfigurationProperties("article")
public class ArticleService {

    static final String SOURCE = "原创";
    private ArticleRepository articleRepository;
    private List<Integer> recommendNumber;

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

    public void updateArticleStatus(String id, ArticleStatus status) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(status);
        articleRepository.updateById(article);
    }

    public List<ArticleBrief> getRecommendArticle(int level) {
        int number = recommendNumber.get(level);
        List<ArticleBrief> articles = articleRepository.getRecommendArticle(level, number);

        if (articles.size() < number) {
            List<ArticleBrief> others = articleRepository.getTopByViewCount(number);
            articles.addAll(others);
        }

        return articles;
    }

    public List<Article> getPersonalArticle(String memberId) {
        Member member = new Member();
        member.setId(memberId);
        return articleRepository.findByMember(member);
    }

    public Page<ArticleBrief> getArticlePage(ArticleReq req) {

        Page<ArticleBrief> page = null;
        Sort sort = Sort.by(Sort.Direction.DESC, "view_count");
        switch (req.getType()) {
            case LATEST:
                sort = Sort.by(Sort.Direction.DESC, "create_time");
                page = articleRepository.getArticlePage(PageRequest.of(req.getPage(), req.getSize(), sort));
                break;
            case HOT:
                page = articleRepository.getArticlePage(PageRequest.of(req.getPage(), req.getSize(), sort));
            case CATEGORY:
                page = articleRepository.getArticlePageByCategory(req.getCategoryId(),PageRequest.of(req.getPage(), req.getSize(), sort));
        }


        return page;
    }
}
