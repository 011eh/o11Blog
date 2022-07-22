package com.o11eh.o11blog.servicebase.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

@Data
public class PageReq {

    private long current;

    private long size;
    private String keyword;
}
