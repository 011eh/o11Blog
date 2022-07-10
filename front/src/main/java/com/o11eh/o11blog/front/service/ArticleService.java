package com.o11eh.o11blog.front.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.o11eh.o11blog.front.repository.ArticleRepository;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import com.o11eh.o11blog.servicebase.entity.front.Member;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleVo;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {

    private ArticleRepository articleRepository;

    public void create(ArticleVo articleVo) {
        Article article = new Article();
        BeanUtil.copyProperties(articleVo, article, CopyOptions.create().ignoreError());
        String memberId = StpUtil.getLoginIdAsString();
        Member member = new Member();
        member.setId(memberId);
        article.setMember(member);

        if (article.getStatus() == null || !article.getStatus().equals(ArticleStatus.DRAFT)) {
            article.setStatus(ArticleStatus.AUDITING);
        }
        List<String> categories = articleVo.getCategories();



        articleRepository.save(article);
    }
}
