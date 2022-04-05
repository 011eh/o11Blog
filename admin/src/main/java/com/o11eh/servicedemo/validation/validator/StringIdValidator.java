package com.o11eh.servicedemo.validation.validator;

import com.o11eh.servicedemo.validation.StringId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringIdValidator implements ConstraintValidator<StringId, String> {

    private boolean required;
    private int length;

    @Override
    public void initialize(StringId annotation) {
        ConstraintValidator.super.initialize(annotation);
        required = annotation.required();
        length = annotation.length();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && value.length() == length) {
            return true;
        } else return value == null && !required;
    }
}
