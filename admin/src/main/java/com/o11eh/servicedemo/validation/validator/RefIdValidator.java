package com.o11eh.servicedemo.validation.validator;

import com.o11eh.servicedemo.validation.RefId;

import javax.validation.ConstraintValidatorContext;

public class RefIdValidator extends AbstractRefIdValidator<String> {

    private String tableName;

    @Override
    public void initialize(RefId annotation) {
        super.initialize(annotation);
        tableName = annotation.tableName();
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return id == null || baseMapper.recordExists(tableName, id);
    }
}
