package com.o11eh.servicedemo.validation.validator;

import com.o11eh.servicedemo.validation.RefId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class RefIdListValidator implements ConstraintValidator<RefId, List<String>> {

    @Override
    public void initialize(RefId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> ids, ConstraintValidatorContext context) {
        System.out.println(ids);
        return true;
    }
}
