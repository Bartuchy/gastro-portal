package com.gastro.portal.auth;

import com.gastro.portal.account.UserAccountEntity;
import com.gastro.portal.account.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    private final UserAccountRepository userAccountRepository;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String verificationCode
                = ((CustomUsernamePasswordAuthenticationToken) auth)
                .getVerificationCode();
        UserAccountEntity userAccountEntity = userAccountRepository.findUserAccountEntityByUsername(auth.getName())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (userAccountEntity.getIsUsing2FA()) {
            Totp totp = new Totp(userAccountEntity.getSecret());
            if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
                throw new BadCredentialsException("Invalid verification code");
            }
        }

        Authentication result = super.authenticate(auth);
        return new CustomUsernamePasswordAuthenticationToken(
                userAccountEntity, result.getCredentials(), verificationCode, result.getAuthorities());
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomUsernamePasswordAuthenticationToken.class);
    }
}
