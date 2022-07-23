package com.o11eh.o11blog.article.controller;


import com.o11eh.o11blog.article.service.ArticleService;
import com.o11eh.o11blog.servicebase.entity.PageReq;
import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "文章接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ArticleApi {

    private final ArticleService articleService;

    @ApiOperation("分页")
    @GetMapping("articlePage")
    public Result articlePage(PageReq pageReq) {
        Page<Article> page = articleService.getArticlePage(pageReq);
        return Result.pageResult(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }

    @ApiOperation("管理员审核")
    @PostMapping("audit")
    public Result audit(@RequestBody List<String> articleIds) {
        articleService.audit(articleIds);
        return Result.success();
    }

}
