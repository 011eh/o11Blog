package com.o11eh.servicedemo.admin.config.validation.validator;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.o11eh.servicedemo.admin.mapper.BaseMapper;
import com.o11eh.servicedemo.admin.config.validation.ColumnsUnique;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ColumnUniqueValidator implements ConstraintValidator<ColumnsUnique, Object> {

    private String tableName;
    private String[] properties;

    @Autowired
    private BaseMapper baseMapper;

    @Override
    public void initialize(ColumnsUnique annotation) {
        tableName = annotation.tableName();
        properties = annotation.properties();
    }

    @Override
    public boolean isValid(Object entry, ConstraintValidatorContext context) {
        String id = (String) ReflectUtil.getFieldValue(entry, "id");
        for (String property : properties) {
            Object value = ReflectUtil.getFieldValue(entry, property);
            boolean columnUnique = baseMapper.columnUnique(tableName, StrUtil.toUnderlineCase(property), value, id);
            if (!columnUnique) {
                return false;
            }
        }
        return true;
    }
}
