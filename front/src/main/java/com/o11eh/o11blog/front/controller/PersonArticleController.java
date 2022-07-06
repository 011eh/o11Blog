package com.o11eh.o11blog.front.controller;

import com.o11eh.o11blog.servicebase.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("person")
public class PersonArticleController {
    public Result create() {
        return Result.success();
    }
}
