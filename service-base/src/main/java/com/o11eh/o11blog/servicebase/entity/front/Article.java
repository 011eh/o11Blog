package com.o11eh.o11blog.servicebase.entity.front;

import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
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

    @ManyToOne
    private Member member;

    @ManyToMany
    private List<ArticleSort> articleSortList;

    @ManyToMany
    private List<Tag> tagList;
}
