package com.o11eh.o11blog.article.controller;

import com.o11eh.o11blog.article.repository.projection.ArticleBrief;
import com.o11eh.o11blog.article.service.ArticleService;
import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "首页")
@RestController
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private ArticleService articleService;


    @ApiOperation("推荐")
    @GetMapping("recommend")
    public Result getArticleRecommend(int level) {
        List<Article> articles = articleService.getRecommendArticle(level);
        return Result.success(articles);
    }

    @ApiOperation("博客分页")
    @GetMapping("articlePage")
    public Result getArticlePage(ArticleReq req) {
        Page<ArticleBrief> page = articleService.getArticlePage(req);
        return Result.pageResult(page.getNumber(), page.getSize(), page.getTotalElements(), page.get().collect(Collectors.toList()));
    }

    @ApiOperation("文章内容")
    @GetMapping("article/{id}")
    public Result getArticle(@PathVariable String id) {
        Article article = articleService.getArticle(id);
        return Result.success(article);
    }

    @ApiOperation("点赞")
    @PostMapping("likeAction")
    public Result likeArticle(String aid, @RequestParam(defaultValue = "like", required = false) String action) {
        articleService.likeAction(aid, action);
        return Result.success();
    }
}
