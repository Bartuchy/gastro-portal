package com.gastro.portal.common.exception.handler;

public record ApiFieldError(
        String field,
        String code,
        String message
) {}