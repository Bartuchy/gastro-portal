package com.gastro.portal.auth.mapper;

import com.gastro.portal.account.UserAccountEntity;
import com.gastro.portal.account.mapper.UserAccountMapper;
import com.gastro.portal.auth.dto.RegisterRequestDto;
import com.gastro.portal.user.mapper.UserMapper;
import com.gastro.portal.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationRequestMapper {
    private final UserMapper userMapper;
    private final UserAccountMapper userAccountMapper;

    public UserAccountUserAggregate mapFrom(RegisterRequestDto request) {
        UserEntity user = userMapper.fromRegistrationRequestDto(request);
        UserAccountEntity account = userAccountMapper.fromRegisterRequestDto(request);

        account.setUser(user);
        user.setUserAccount(account);
        return new UserAccountUserAggregate(user, account);
    }
}
