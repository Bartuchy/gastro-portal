package com.gastro.portal.mailing.token;

import com.gastro.portal.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConfirmationTokenFactory {

    public ConfirmationToken createConfirmationToken(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();

        return ConfirmationToken.builder()
                .token(token)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(900))
                .userEntity(userEntity)
                .build();
    }
}
