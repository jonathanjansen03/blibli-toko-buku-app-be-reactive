package com.example.bliblitokobukuappbereactive.constraint;

import com.example.bliblitokobukuappbereactive.validator.ExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsConstraint {
    String message() default "The book you're looking for is not present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}