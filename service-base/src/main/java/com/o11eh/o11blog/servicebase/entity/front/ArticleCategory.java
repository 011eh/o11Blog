package com.o11eh.o11blog.servicebase.entity.front;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import com.o11eh.o11blog.servicebase.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
@Entity(name = "front_article_category")
public class ArticleCategory extends BaseEntry {

    private String name;
    private String summary;
    private String clickCount;
    private String sort;

    @Enumerated
    private Status status;

    @JsonIgnoreProperties("categories")
    @ManyToMany(mappedBy = "categories")
    private List<Article> articles;

}
