package com.gastro.portal.config.mailing.token;

import com.gastro.portal.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    private User user;
}
