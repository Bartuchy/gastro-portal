package com.gastro.portal.auth.exception;

import com.gastro.portal.auth.dto.RegisterRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException{

    public UserExistsException(RegisterRequestDto registerRequestDto) {
        super(String.format("User with email %s already exists", registerRequestDto.getEmail()));
    }
}
