package com.markiewicz.recipes.user;

import com.markiewicz.recipes.role.RoleService;
import com.markiewicz.recipes.user.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.markiewicz.recipes.security.SecurityUtil.USER;


@Component
@RequiredArgsConstructor
public class UserFactory {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRegisterDto registerDto) {
        return User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(roleService.getRoleByName(USER))
                .build();
    }
}
