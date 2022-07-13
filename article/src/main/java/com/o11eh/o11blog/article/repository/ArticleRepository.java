package com.o11eh.o11blog.article.repository;

import com.o11eh.o11blog.article.repository.projection.ArticleBrief;
import com.o11eh.o11blog.servicebase.config.jpaconfig.BaseRepository;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import com.o11eh.o11blog.servicebase.entity.front.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends BaseRepository<Article>, JpaSpecificationExecutor<Article> {
    Article findByIdAndMemberId(String id, String memberId);

    List<Article> findByMember(Member member);

    @Query(value = "select a.id, title, a.create_time createTime, image_url imageUrl, c.name  category from front_article a join front_article_and_category ac on a.id = ac.article_id join front_article_category c on c.id = ac.category_id where a.status = 2 and a.recommend_level = :level limit :count", nativeQuery = true)
    List<ArticleBrief> getRecommendArticle(int level, int count);

    @Query(value = "select a.id, title, a.create_time createTime, image_url imageUrl, c.name  categoryName from front_article a join front_article_and_category ac on a.id = ac.article_id join front_article_category c on c.id = ac.category_id where a.status = 2 order by view_count desc limit :count", nativeQuery = true)
    List<ArticleBrief> getTopByViewCount(int count);

    @Query(value = "select a.id,a.title,a.summary,a.view_count  viewCount,a.like_count  likeCount,a.image_url   imageUrl,a.create_time createTime,m.id          memberId,m.nick_name   nickName,m.avatar from front_article a join front_article_tag at on a.id = at.article_id join front_tag t on at.tag_id = t.id join front_member m on a.member_id = m.id where a.status = 2",
            countQuery = "select count(*) from front_article where status = 2",
            nativeQuery = true)
    Page<ArticleBrief> getArticlePage(Pageable pageRequest);

    @Query(value = "select a.id, a.title, a.summary, a.view_count  viewCount, a.like_count  likeCount, a.image_url   imageUrl, a.create_time createTime, m.id          memberId, m.nick_name   nickName, m.avatar,t.id tagId,t.name tagName from front_article a join front_article_tag at on a.id = at.article_id join front_tag t on at.tag_id = t.id join front_member m on a.member_id = m.id join front_article_and_category ac on ac.article_id = a.id where a.status = 2 and ac.category_id = :categoryId",
            countQuery = "select count(*) from front_article a join front_article_and_category ac on ac.article_id = a.id where status = 2 and ac.category_id = :categoryId",
            nativeQuery = true)
    Page<ArticleBrief> getArticlePageByCategory(@Param("categoryId") String categoryId, Pageable pageRequest);
}
