package com.o11eh.o11blog.search.repository;

import com.o11eh.o11blog.search.entity.ESArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESArticleRepository extends ElasticsearchRepository<ESArticle, String> {
}
