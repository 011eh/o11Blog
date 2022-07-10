package com.o11eh.o11blog.servicebase.entity.front;

import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import com.o11eh.o11blog.servicebase.enums.ArticleStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

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
}
