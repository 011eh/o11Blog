package com.o11eh.servicedemo.validation.validator;

import com.o11eh.servicedemo.validation.StringId;

import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

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
