package com.o11eh.servicedemo.admin.config.validation.validator;

import com.o11eh.servicedemo.admin.config.validation.RefId;
import com.o11eh.servicedemo.admin.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;

public abstract class AbstractRefIdValidator<T> implements ConstraintValidator<RefId, T> {

    protected String tableName;

    @Autowired
    protected BaseMapper baseMapper;

    @Override
    public void initialize(RefId annotation) {
        tableName = annotation.value();
    }
}
