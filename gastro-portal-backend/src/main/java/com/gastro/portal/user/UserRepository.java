package com.gastro.portal.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findUserByDisplayName(String username);
    boolean existsByDisplayNameIgnoreCase(String displayName);
}
