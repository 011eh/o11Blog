package com.o11eh.o11blog.front.controller;

import com.o11eh.o11blog.front.service.ArticleService;
import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.front.vo.ArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "个人中心")
@RestController
@RequestMapping("person")
@AllArgsConstructor
public class PersonController {

    private ArticleService articleService;

    @ApiOperation("创建博客")
    @PostMapping("createArticle")
    public Result createArticle(@RequestBody ArticleVo articleVo) {
        articleService.create(articleVo);
        return Result.success();
    }
}
