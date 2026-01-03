package com.gastro.portal.auth.dto;

import com.gastro.portal.common.annotation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RegisterRequestDto {

    @NotBlank
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "username ma niepoprawne znaki")
    private String username;

    @NotBlank
    @Email
    @Size(max = 254)
    private String displayName;

    @StrongPassword
    private String password;
    private Boolean using2FA;
}
