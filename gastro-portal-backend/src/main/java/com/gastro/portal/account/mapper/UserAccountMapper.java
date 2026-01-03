package com.gastro.portal.account.mapper;

import com.gastro.portal.account.UserAccountEntity;
import com.gastro.portal.auth.dto.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccountMapper {
    private final PasswordEncoder passwordEncoder;

    public UserAccountEntity fromRegisterRequestDto(RegisterRequestDto request) {
        return UserAccountEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .isUsing2FA(request.getUsing2FA())
                .isEnabled(true)
                .isNonLocked(true)
                .build();
    }
}
