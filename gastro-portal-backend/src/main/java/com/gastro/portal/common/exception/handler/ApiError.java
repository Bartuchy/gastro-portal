package com.gastro.portal.common.exception.handler;

import java.util.List;

public record ApiError(
        String code,
        List<ApiFieldError> fields
) {}