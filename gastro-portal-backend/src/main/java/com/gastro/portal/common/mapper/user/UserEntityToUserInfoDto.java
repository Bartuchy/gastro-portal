package com.gastro.portal.common.mapper.user;

import com.gastro.portal.common.mapper.Mapper;
import com.gastro.portal.user.UserEntity;
import com.gastro.portal.user.dto.UserInfoDto;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserInfoDto implements Mapper<UserEntity, UserInfoDto> {
    @Override
    public UserInfoDto map(UserEntity userEntity) {
        return UserInfoDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .isEnabled(userEntity.isEnabled())
                .isNonLocked(userEntity.getIsNonLocked())
                .isUsing2FA(userEntity.getIsUsing2FA())
                .build();
    }
}
