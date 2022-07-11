package com.o11eh.o11blog.front.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.o11eh.o11blog.front.repository.ArticleRepository;
import com.o11eh.o11blog.servicebase.config.BusinessException;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import com.o11eh.o11blog.servicebase.entity.front.Member;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {

    public static final String SOURCE = "原创";
    private ArticleRepository articleRepository;

    public void create(ArticleVo articleVo) {
        Article article = new Article();
        BeanUtil.copyProperties(articleVo, article, CopyOptions.create().ignoreError().setIgnoreProperties("id"));
        String memberId = StpUtil.getLoginIdAsString();
        Member member = new Member();
        member.setId(memberId);
        article.setMember(member);

        article.idToCategories(articleVo.getCategories())
                .idToTags(articleVo.getTags());

        article.setNotPublish();
        // 原创文章，且没有来源，则设置来源信息
        if (article.isOriginal() && article.getSource() == null) {
            article.setSource(SOURCE);
        }

        articleRepository.save(article);
    }

    public void update(ArticleVo articleVo) {
        String memberId = StpUtil.getLoginIdAsString();
        Optional<Article> result = articleRepository.findOne((root, query, criteriaBuilder) ->
                query.where(criteriaBuilder.equal(root.get("id"), articleVo.getId()),
                        criteriaBuilder.equal(root.get("memberId"), new Member().setId(memberId))).getRestriction());

        if (!result.isPresent()) {
            throw BusinessException.e("无法修改文章");
        }

        Article article = result.get();
        BeanUtil.copyProperties(articleVo, article, CopyOptions.create().ignoreError());
        article.setNotPublish();
        articleRepository.updateById(article);
    }
}
