package com.gastro.portal.account;

import com.gastro.portal.auth.exception.UserNotFoundException;
import com.gastro.portal.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void updateUser2FA(String email, boolean using2FA) {
        Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUserEntity = (UserEntity) curAuth.getPrincipal();
        userAccountRepository.updateUser2FA();
    }

    public UserAccountEntity getUserAccountEntityByUsername(String username) {
       return userAccountRepository.findUserAccountEntityByUsername(username)
               .orElseThrow(UserNotFoundException::new);
    }
}
