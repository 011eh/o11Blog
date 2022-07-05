package com.o11eh.o11blog.servicebase.validation;

import com.o11eh.o11blog.servicebase.validation.validator.ColumnUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = ColumnUniqueValidator.class)
public @interface ColumnsUnique {
    String message() default "唯一字段值已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String tableName();

    String[] properties();
}
