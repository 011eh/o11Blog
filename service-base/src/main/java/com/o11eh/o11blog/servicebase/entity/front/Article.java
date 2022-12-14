package com.o11eh.o11blog.servicebase.entity.front;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
@Entity(name = "front_article")
@NoArgsConstructor
public class Article extends BaseEntry {

    private String title;
    private String summary;
    private String content;
    private String imageUrl;

    @Enumerated
    private ArticleStatus status;
    private boolean original;
    private String source;
    private Integer recommendLevel;
    private Integer sort;
    private boolean enableComment;
    private Long viewCount;
    private Long likeCount;

    @Transient
    private Boolean liked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"articles"})  // 序列化递归问题
    private Member member;

    @JsonIgnoreProperties({"articles"})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "front_article_and_category", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<ArticleCategory> categories;

    @JsonIgnoreProperties({"articles"})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "front_article_tag", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    public Article idToTags(List<String> ids) {
        List<Tag> tags = ids.stream().map(id -> {
            Tag tag = new Tag();
            tag.setId(id);
            return tag;
        }).collect(Collectors.toList());
        this.setTags(tags);
        return this;
    }

    public Article idToCategories(List<String> ids) {
        List<ArticleCategory> tags = ids.stream().map(id -> {
            ArticleCategory category = new ArticleCategory();
            category.setId(id);
            return category;
        }).collect(Collectors.toList());
        this.setCategories(tags);
        return this;
    }

    public Article setNotPublish() {
        // 设置状态为审核中
        if (this.status == null || !this.status.equals(ArticleStatus.DRAFT)) {
            this.setStatus(ArticleStatus.AUDITING);
        }
        return this;
    }
}
