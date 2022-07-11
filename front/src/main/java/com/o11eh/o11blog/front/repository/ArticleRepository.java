package com.o11eh.o11blog.front.repository;

import com.o11eh.o11blog.servicebase.entity.front.Article;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleRepository extends BaseRepository<Article>, JpaSpecificationExecutor<Article> {
    Article findByIdAndMemberId(String id, String memberId);
}
