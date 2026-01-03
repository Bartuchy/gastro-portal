package com.gastro.portal.common.exception.handler;

import com.gastro.portal.common.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class CommonControllerExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ApiError handleValidation(MethodArgumentNotValidException ex, Locale locale) {
        List<ApiFieldError> fields = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> new ApiFieldError(
                        err.getField(),
                        err.getDefaultMessage(),
                        messageSource.getMessage(Objects.requireNonNull(err.getDefaultMessage()), null, locale)
                ))
                .toList();

        return new ApiError(ErrorCodes.VALIDATION_FAILED, fields);
    }
}
