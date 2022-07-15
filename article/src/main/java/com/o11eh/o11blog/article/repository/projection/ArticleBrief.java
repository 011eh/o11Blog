package com.o11eh.o11blog.article.repository.projection;

import com.o11eh.o11blog.servicebase.entity.front.Tag;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleBrief {

    String getId();

    String getTitle();

    String getSummary();

    Integer getViewCount();

    Integer getLikeCount();

    LocalDateTime getCreateTime();

    String getImageUrl();

    List<String> getCategory();

    String getMemberId();

    String getNickname();

    String getAvatar();

    List<Tag> getTags();

}
