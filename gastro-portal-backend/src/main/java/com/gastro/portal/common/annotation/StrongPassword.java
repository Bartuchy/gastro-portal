package com.gastro.portal.common.annotation;

import com.gastro.portal.auth.StrongPasswordValidator;
import com.gastro.portal.common.exception.ErrorCodes;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default ErrorCodes.STRONG_PASSWORD;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
