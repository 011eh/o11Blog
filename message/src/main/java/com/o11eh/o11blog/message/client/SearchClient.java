package com.o11eh.o11blog.message.client;

import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("search")
public interface SearchClient {

    @PostMapping("update")
    Result update(Article article);

}
