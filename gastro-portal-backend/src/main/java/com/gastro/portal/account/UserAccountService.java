package com.gastro.portal.account;

import com.gastro.portal.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;

    public boolean isUserAccountUsing2FA(String username) {
        return userAccountRepository
                .findUserAccountEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s not found", username)
                ))
                .getIsUsing2FA();
    }

    public UserAccountEntity getUserAccountEntityByUsername(String username) {
       return userAccountRepository.findUserAccountEntityByUsername(username)
               .orElseThrow(UserNotFoundException::new);
    }
}
