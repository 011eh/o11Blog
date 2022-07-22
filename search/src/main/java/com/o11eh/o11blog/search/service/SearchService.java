package com.o11eh.o11blog.search.service;

import com.o11eh.o11blog.search.entity.ESArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SearchService {
    private final ElasticsearchRestTemplate elasticsearch;

    public void init() {
        elasticsearch.indexOps(ESArticle.class).delete();
        elasticsearch.indexOps(ESArticle.class).create();
    }
}
