package com.gastro.portal.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE UserAccount ua SET ua.isEnabled = TRUE WHERE ua.username = :email")
    void enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserAccount ua SET ua.isNonLocked = TRUE WHERE ua.username = :email")
    void unlockAccount(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserAccount ua SET ua.isUsing2FA = TRUE WHERE ua.username = ?1")
    void updateUser2FA();

    Optional<UserAccountEntity> findUserAccountEntityByUsername(String username);

    boolean existsUserAccountEntityByUsernameIgnoreCase(String username);
}
