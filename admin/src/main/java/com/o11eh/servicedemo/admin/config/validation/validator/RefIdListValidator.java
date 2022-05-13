package com.o11eh.servicedemo.admin.config.validation.validator;

import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Set;

public class RefIdListValidator extends AbstractRefIdValidator<List<String>> {

    @Override
    public boolean isValid(List<String> ids, ConstraintValidatorContext context) {
        Set<String> idSet = baseMapper.selectIdIfExist(tableName, ids);

        for (String id : ids) {
            if (!idSet.contains(id)) {
                return false;
            }
        }
        return true;
    }
}
