package com.o11eh.o11blog.admin.config.validation.validator;

import com.o11eh.o11blog.admin.config.validation.StringId;

import javax.validation.ConstraintValidatorContext;

public class StringIdValidator extends AbstractStringIdValidator<String> {

    @Override
    public void initialize(StringId annotation) {
        required = annotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && pattern.matcher(value).matches()) {
            return true;
        } else return value == null && !required;
    }
}
