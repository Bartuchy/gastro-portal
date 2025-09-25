package com.gastro.portal.user.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponseDto {
    private boolean using2FA;
    private String qrCode;
}
