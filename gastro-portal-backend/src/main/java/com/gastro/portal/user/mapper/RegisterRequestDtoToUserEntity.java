package com.gastro.portal.user.mapper;

import com.gastro.portal.common.mapper.Mapper;
import com.gastro.portal.user.UserEntity;
import com.gastro.portal.user.auth.dto.RegisterRequestDto;
import com.gastro.portal.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.gastro.portal.config.security.SecurityUtil.USER;

@Component
@RequiredArgsConstructor
public class RegisterRequestDtoToUserEntity implements Mapper<RegisterRequestDto, UserEntity> {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity map(RegisterRequestDto registerRequestDto) {
        return UserEntity.builder()
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .roleEntity(roleService.getRoleByName(USER))
                .isUsing2FA(registerRequestDto.getUsing2FA())
                .secret(Base32.random())
                .isEnabled(true)
                .isNonLocked(true)
                .build();
    }
}
