package com.o11eh.o11blog.servicebase.validation.validator;

import com.o11eh.o11blog.servicebase.validation.RefId;
import com.o11eh.o11blog.servicebase.mapper.ValidatorMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;

public abstract class AbstractRefIdValidator<T> implements ConstraintValidator<RefId, T> {

    protected String tableName;

    @Autowired
    protected ValidatorMapper validatorMapper;

    @Override
    public void initialize(RefId annotation) {
        tableName = annotation.value();
    }
}
