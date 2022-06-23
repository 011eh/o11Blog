package com.o11eh.servicedemo.front.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Length(max = 19)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.o11eh.servicedemo.front.config.SnowFlakeIdGenerator")
    private String id;

    @CreatedBy
    @Column(updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createTime;


    @LastModifiedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updatedBy;

    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateTime;

}
