package com.gastro.portal.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isEnabled = TRUE WHERE u.email = :email")
    void enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isNonLocked = TRUE WHERE u.email = :email")
    void unlockAccount(String email);

    Optional<UserEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isUsing2FA = TRUE WHERE u.email = ?1")
    void updateUser2FA();


    Optional<UserEntity> findUserByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
