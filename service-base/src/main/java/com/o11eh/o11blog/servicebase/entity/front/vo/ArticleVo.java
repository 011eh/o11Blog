package com.o11eh.o11blog.servicebase.entity.front.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    private String title;
    private String summary;
    private String content;
    private String imageUrl;
    private Boolean original;
    private String source;
    private Boolean enableComment;
    private List<String> categories;
    private List<String> tags;

}
