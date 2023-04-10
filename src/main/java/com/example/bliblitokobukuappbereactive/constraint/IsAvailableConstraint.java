package com.example.bliblitokobukuappbereactive.constraint;

import com.example.bliblitokobukuappbereactive.validator.IsAvailableValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsAvailableValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsAvailableConstraint {
    String message() default "The book you are looking for is not present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
