package com.gastro.portal.user.mapper;

import com.gastro.portal.auth.dto.RegisterRequestDto;
import com.gastro.portal.user.UserEntity;
import com.gastro.portal.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public UserEntity fromRegistrationRequestDto(RegisterRequestDto registerRequestDto) {
        return UserEntity.builder()
                .displayName(registerRequestDto.getUsername())
                .build();
    }

    public UserInfoDto fromUserEntityToUserInfoDto(UserEntity userEntity) {
        return UserInfoDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getDisplayName())
                .build();
    }
}
