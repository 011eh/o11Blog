package com.o11eh.o11blog.front.controller;

import com.o11eh.o11blog.front.service.ArticleService;
import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleVo;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("获取个人热门文章")
    @PostMapping("articleList")
    public Result getPersonalHotArticlePage() {
        return Result.success();
    }
}
