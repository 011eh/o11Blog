package com.o11eh.servicedemo.validation.validator;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.BaseEntry;
import com.o11eh.servicedemo.admin.mapper.BaseMapper;
import com.o11eh.servicedemo.admin.mapper.BaseMapperO;
import com.o11eh.servicedemo.validation.ColumnsUnique;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ColumnUniqueValidator implements ConstraintValidator<ColumnsUnique, BaseEntry> {

    @Override
    public void initialize(ColumnsUnique annotation) {
    }

    @Override
    public boolean isValid(BaseEntry entry, ConstraintValidatorContext context) {
        return false;
    }
}
