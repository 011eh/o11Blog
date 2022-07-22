package com.o11eh.o11blog.search.entity;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "article")
public class ESArticle {
    private String id;
}
