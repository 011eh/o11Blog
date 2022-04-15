package com.o11eh.servicedemo.validation.validator;

import com.o11eh.servicedemo.validation.StringId;

import javax.validation.ConstraintValidator;
import java.util.regex.Pattern;

public abstract class AbstractStringIdValidator<T> implements ConstraintValidator<StringId, T> {

    protected boolean required;
    protected Pattern pattern = Pattern.compile("\\d{19}");

    @Override
    public void initialize(StringId annotation) {
        required = annotation.required();
    }
}
