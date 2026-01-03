package com.gastro.portal.mailing.token;

import com.gastro.portal.account.UserAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConfirmationTokenFactory {

    public ConfirmationToken createConfirmationToken(UserAccountEntity userAccountEntity) {
        String token = UUID.randomUUID().toString();

        return ConfirmationToken.builder()
                .token(token)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(900))
                .userAccount(userAccountEntity)
                .build();
    }
}
