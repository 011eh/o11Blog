package com.o11eh.o11blog.search.client;

import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("article")
public interface ArticleClient {
    @GetMapping("api/articlePage")
    Article articlePage(@RequestParam("current") int current, @RequestParam("size") long size);
}
