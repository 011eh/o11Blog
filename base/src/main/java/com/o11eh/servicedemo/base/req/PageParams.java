package com.o11eh.servicedemo.base.req;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

@Data
public class PageParams {
    @Min(value = 1)
    private Long current;
    @Range(min = 1, max = 100)
    private Long size;
}
