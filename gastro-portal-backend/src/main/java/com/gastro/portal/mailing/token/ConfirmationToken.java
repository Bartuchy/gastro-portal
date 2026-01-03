package com.gastro.portal.mailing.token;

import com.gastro.portal.account.UserAccountEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "confirmation_token",
        indexes = @Index(name = "idx_confirmation_token_token", columnList = "token"),
        uniqueConstraints = @UniqueConstraint(name = "uk_confirmation_token_token", columnNames = "token"))
public class ConfirmationToken {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    private Instant confirmedAt;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccountEntity userAccount;
}
