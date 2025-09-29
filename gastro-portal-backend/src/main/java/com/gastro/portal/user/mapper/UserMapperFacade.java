package com.gastro.portal.user.mapper;

import com.gastro.portal.common.mapper.Mapper;
import com.gastro.portal.user.UserEntity;
import com.gastro.portal.user.auth.dto.RegisterRequestDto;
import com.gastro.portal.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperFacade {
    private final Mapper<RegisterRequestDto, UserEntity> registerRequestDtoUserEntityMapper;
    private final Mapper<UserEntity, UserInfoDto> userEntityUserInfoDtoMapper;

    public UserEntity toUserEntity(RegisterRequestDto registerRequestDto) {
        return registerRequestDtoUserEntityMapper.map(registerRequestDto);
    }

    public UserInfoDto toUserInfoDto(UserEntity userEntity) {
        return userEntityUserInfoDtoMapper.map(userEntity);
    }
}
