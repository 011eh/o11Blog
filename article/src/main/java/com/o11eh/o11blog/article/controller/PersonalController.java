package com.o11eh.o11blog.article.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.o11blog.article.service.ArticleService;
import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleVo;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "博客")
@RestController
@RequestMapping("article/personal")
@AllArgsConstructor
public class PersonalController {

    private ArticleService articleService;

    @ApiOperation("创建文章")
    @PostMapping("createArticle")
    public Result createArticle(@RequestBody ArticleVo articleVo) {
        articleService.create(articleVo);
        return Result.success();
    }

    @ApiOperation("更新文章")
    @PostMapping("person/updateArticle")
    public Result updateArticle(@RequestBody ArticleVo articleVo) {
        articleService.update(articleVo);
        return Result.success();
    }

    @ApiOperation("更新文章状态")
    @PostMapping("updateArticleStatus")
    public Result updateArticleStatus(String id, ArticleStatus status) {
        articleService.updateArticleStatus(id, status);
        return Result.successShowMsg();
    }

    @ApiOperation("获取个人文章")
    @PostMapping("articleList")
    public Result getPersonalHotArticlePage() {
        String memberId = StpUtil.getLoginIdAsString();
        List<Article> articles = articleService.getPersonalArticle(memberId);
        return Result.success(articles);
    }
}
