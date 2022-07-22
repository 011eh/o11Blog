package com.o11eh.o11blog.search.entity;

import com.o11eh.o11blog.servicebase.entity.front.ArticleCategory;
import com.o11eh.o11blog.servicebase.entity.front.Tag;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Document(indexName = "article")
public class ESArticle {

    private String id;
    private String title;
    private String summary;
    private String content;
    private String imageUrl;
    private ArticleStatus status;
    private boolean original;
    private String source;
    private Integer recommendLevel;
    private Integer sort;
    private boolean enableComment;
    private Long viewCount;
    private Long likeCount;
    private Boolean liked;
    private String memberId;
    private String member;
    private List<ArticleCategory> categories;
    private List<Tag> tags;

    @Field(type = FieldType.Date)
    private LocalDateTime createDate;
}
