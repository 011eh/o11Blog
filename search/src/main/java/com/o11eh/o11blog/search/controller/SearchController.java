package com.o11eh.o11blog.search.controller;

import com.o11eh.o11blog.search.client.ArticleClient;
import com.o11eh.o11blog.search.service.SearchService;
import com.o11eh.o11blog.servicebase.entity.PageReq;
import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ArticleClient articleClient;
    private final SearchService searchService;

    @PostMapping("init")
    public Result init() {
        searchService.init();
        return Result.success();
    }

    @GetMapping("test")
    public Article test(PageReq pageReq) {
        return articleClient.articlePage(Math.toIntExact(pageReq.getCurrent()), pageReq.getSize());
    }

    @GetMapping("test2")
    public String name() {
        return "hello";
    }
}
