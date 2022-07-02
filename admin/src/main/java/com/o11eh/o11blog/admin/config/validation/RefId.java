package com.o11eh.o11blog.admin.config.validation;

import com.o11eh.o11blog.admin.config.validation.validator.RefIdListValidator;
import com.o11eh.o11blog.admin.config.validation.validator.RefIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@StringId(required = false)
@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {RefIdValidator.class, RefIdListValidator.class})
public @interface RefId {
    String message() default "引用的记录不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();
}
