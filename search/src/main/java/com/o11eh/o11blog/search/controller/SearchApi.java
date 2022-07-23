package com.o11eh.o11blog.search.controller;

import com.o11eh.o11blog.search.entity.ESArticle;
import com.o11eh.o11blog.search.service.SearchService;
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
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "搜索")
@RestController
@RequiredArgsConstructor
public class SearchApi {

    private final SearchService searchService;

    @ApiOperation("初始化")
    @PostMapping("init")
    public Result init() {
        searchService.init();
        return Result.success();
    }

    @ApiOperation("搜索")
    @GetMapping("search")
    public Result search(PageReq req) {
        Page<ESArticle> page = searchService.search(req);
        return Result.pageResult(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }

    @ApiOperation("更新")
    @PostMapping("update")
    public Result update(@RequestBody Article article) {
        searchService.update(article);
        return Result.success();
    }
}
