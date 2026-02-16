package com.gastro.portal.user;

import com.gastro.portal.account.UserAccountRepository;
import com.gastro.portal.user.mapper.UserMapper;
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
    private final UserAccountRepository userAccountRepository;
    private final UserMapper userMapper;


    public List<UserInfoDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::fromUserEntityToUserInfoDto).toList();
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserByDisplayName(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with email %s not found", email)
        ));
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
