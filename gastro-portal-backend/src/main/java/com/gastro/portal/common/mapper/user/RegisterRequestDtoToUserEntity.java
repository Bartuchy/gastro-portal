package com.gastro.portal.common.mapper.user;

import com.gastro.portal.auth.dto.RegisterRequestDto;
import com.gastro.portal.common.mapper.Mapper;
import com.gastro.portal.role.RoleService;
import com.gastro.portal.user.UserEntity;
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
                .nickname(registerRequestDto.getUsername())
                .username(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .roleEntity(roleService.getRoleByName(USER))
                .isUsing2FA(registerRequestDto.getUsing2FA())
                .secret(Base32.random())
                .isEnabled(true)
                .isNonLocked(true)
                .build();
    }
}
