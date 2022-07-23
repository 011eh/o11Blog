package com.o11eh.o11blog.servicebase.entity.front.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
public class ArticleVo {

    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String summary;

    @NotBlank
    private String content;
    private String imageUrl;
    private boolean original;
    private String source;
    private boolean enableComment;

    @NotEmpty
    private List<String> categories;
    private List<String> tags;

}
