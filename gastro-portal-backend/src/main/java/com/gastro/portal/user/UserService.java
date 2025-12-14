package com.gastro.portal.user;

import com.gastro.portal.common.mapper.user.UserMapperFacade;
import com.gastro.portal.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapperFacade userMapperFacade;

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    public void unlockAccount(String email) {
        userRepository.unlockAccount(email);
    }

    public List<UserInfoDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapperFacade::toUserInfoDto).toList();
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserByUsername(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with email %s not found", email)
        ));
    }

    public Optional<UserEntity> getUSerOptByEmail(String email) {
        return userRepository.findUserByUsername(email);
    }

    public void updateUser2FA(String email, boolean using2FA) {
        Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUserEntity = (UserEntity) curAuth.getPrincipal();
        userRepository.updateUser2FA();
    }

    public boolean isUserUsing2FA(String email) {
        return userRepository
                .findUserByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s not found", email)
                ))
                .getIsUsing2FA();
    }

    public void saveGeneratedQrCode(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return getUserByEmail(email);
    }
}
