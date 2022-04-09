package com.o11eh.servicedemo.admin.entry;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

@Data
public class PageReq {

    @Min(value = 1)
    private Long current;

    @Range(min = 1, max = 100)
    private Long size;
    private String keyword;
}
