package com.gastro.portal.user;

import com.gastro.portal.user.auth.dto.RegisterRequestDto;
import com.gastro.portal.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.gastro.portal.config.security.SecurityUtil.USER;

@Component
@RequiredArgsConstructor
public class UserFactory {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public User createUser(RegisterRequestDto request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleService.getRoleByName(USER))
                .isUsing2FA(request.getUsing2FA())
                .build();
    }
}
