package com.o11eh.o11blog.servicebase.entity.front;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
@Entity(name = "front_article")
public class Article extends BaseEntry {

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
    private Integer viewCount;
    private Integer likeCount;

    @ManyToOne
    private Member member;

    @ManyToMany
    @JoinTable(name = "front_article_and_category", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<ArticleCategory> categories;

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
