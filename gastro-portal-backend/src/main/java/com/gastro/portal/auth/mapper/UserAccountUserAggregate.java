package com.gastro.portal.auth.mapper;

import com.gastro.portal.account.UserAccountEntity;
import com.gastro.portal.user.UserEntity;

public record UserAccountUserAggregate(UserEntity user, UserAccountEntity userAccount){}
