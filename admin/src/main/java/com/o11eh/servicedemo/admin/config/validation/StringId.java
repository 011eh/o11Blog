package com.o11eh.servicedemo.admin.config.validation;

import com.o11eh.servicedemo.admin.config.validation.validator.StringIdListValidator;
import com.o11eh.servicedemo.admin.config.validation.validator.StringIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {StringIdValidator.class, StringIdListValidator.class})
public @interface StringId {

    String message() default "Id格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean required() default true;

}
