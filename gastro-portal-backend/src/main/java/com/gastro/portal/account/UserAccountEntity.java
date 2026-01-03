package com.gastro.portal.account;

import com.gastro.portal.role.RoleEntity;
import com.gastro.portal.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.jboss.aerogear.security.otp.api.Base32;

@Builder
@Setter
@Getter
@Entity(name = "UserAccount")
@Table(name = "user_account", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_account_username", columnNames = "username")
})
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean isEnabled;
    private Boolean isNonLocked;
    private Boolean isUsing2FA;
    private String secret = Base32.random();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
