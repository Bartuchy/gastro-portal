package com.gastro.portal.auth.validator;

import com.gastro.portal.account.UserAccountRepository;
import com.gastro.portal.auth.dto.RegisterRequestDto;
import com.gastro.portal.auth.exception.UserExistsException;
import com.gastro.portal.common.exception.ErrorCodes;
import com.gastro.portal.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationValidator {
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;

    public void assertRegistrationDataValidation(RegisterRequestDto request) {
        if (userAccountRepository.existsUserAccountEntityByUsernameIgnoreCase(request.getUsername())) {
            throw new UserExistsException(ErrorCodes.EMAIL_TAKEN);
        }
        if (userRepository.existsByDisplayNameIgnoreCase(request.getDisplayName())) {
            throw new UserExistsException(ErrorCodes.USERNAME_TAKEN);
        }

    }

}
