package com.o11eh.servicedemo.validation.validator;

import com.o11eh.servicedemo.admin.mapper.BaseMapper;
import com.o11eh.servicedemo.validation.RefId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;

public abstract class AbstractRefIdValidator<T> implements ConstraintValidator<RefId, T> {
    @Autowired
    protected BaseMapper baseMapper;
}
