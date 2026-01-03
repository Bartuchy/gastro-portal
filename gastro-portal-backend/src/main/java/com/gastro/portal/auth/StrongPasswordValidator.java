package com.gastro.portal.auth;

import com.gastro.portal.common.annotation.StrongPassword;
import com.gastro.portal.common.exception.ErrorCodes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private static final int MIN_LENGTH = 8;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null) return false;

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        if (value.length() < MIN_LENGTH || !hasUpper || !hasDigit || !hasSpecial) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    ErrorCodes.STRONG_PASSWORD
            ).addConstraintViolation();
            return false;
        }
        return true;
    }

}
