package com.o11eh.servicedemo.validation.validator;

import com.o11eh.servicedemo.admin.service.DBUtilService;
import com.o11eh.servicedemo.validation.RefId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RefIdValidator implements ConstraintValidator<RefId, String> {

    private String tableName;

    @Autowired
    private DBUtilService dbUtilService;

    @Override
    public void initialize(RefId annotation) {
        ConstraintValidator.super.initialize(annotation);
        tableName = annotation.tableName();
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return id == null || dbUtilService.recordExists(tableName, id);
    }
}
