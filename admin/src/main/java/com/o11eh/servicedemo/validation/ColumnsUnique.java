package com.o11eh.servicedemo.validation;

import com.o11eh.servicedemo.validation.validator.ColumnUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull
@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ColumnUniqueValidator.class)
public @interface ColumnsUnique {
    String message() default "已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String tableName();

    String columnName();
}
