package com.o11eh.servicedemo.admin.config.validation.validator;

import com.o11eh.servicedemo.admin.config.validation.StringId;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class StringIdListValidator extends AbstractStringIdValidator<List<String>> {

    @Override
    public void initialize(StringId annotation) {
        required = true;
    }

    @Override
    public boolean isValid(List<String> ids, ConstraintValidatorContext context) {
        for (String id : ids) {
            if (!pattern.matcher(id).matches()) {
                return false;
            }
        }
        return true;
    }
}
