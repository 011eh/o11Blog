package com.o11eh.servicedemo.validation.validator;

import com.o11eh.servicedemo.validation.StringId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class StringIdValidator implements ConstraintValidator<StringId, String> {

    private boolean required;
    private Pattern pattern;

    @Override
    public void initialize(StringId annotation) {
        required = annotation.required();
        pattern = Pattern.compile("[0-9]{19}");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && pattern.matcher(value).matches()) {
            return true;
        } else return value == null && !required;
    }
}
