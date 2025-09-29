package com.gastro.portal.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean isEnabled;
    private Boolean isNonLocked;
    private Boolean isUsing2FA;
}
