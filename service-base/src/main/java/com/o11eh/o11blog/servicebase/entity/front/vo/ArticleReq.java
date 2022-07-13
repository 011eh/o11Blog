package com.o11eh.o11blog.servicebase.entity.front.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ArticleReq {

    private int page;
    private int size;
    private ReqType type;
    private String categoryId;

    @RequiredArgsConstructor
    public enum ReqType {
        LATEST("latest"),
        HOT("hot"),
        CATEGORY("category");

        @JsonValue
        private final String reqType;
    }

}
