package com.gastro.portal.admin;

import com.gastro.portal.account.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserAccountRepository userAccountRepository;

    public void enableUser(String email) {
        userAccountRepository.enableUser(email);
    }

    public void unlockAccount(String email) {
        userAccountRepository.unlockAccount(email);
    }
}
