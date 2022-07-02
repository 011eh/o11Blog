package com.o11eh.o11blog.admin.config.validation.validator;

import javax.validation.ConstraintValidatorContext;

public class RefIdValidator extends AbstractRefIdValidator<String> {

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return id == null || baseMapper.recordExists(tableName, id);
    }
}
