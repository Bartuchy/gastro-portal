package com.gastro.portal.user;

import com.gastro.portal.account.UserAccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record UserPrincipal(UserAccountEntity userAccount) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userAccount.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userAccount.getIsNonLocked();
    }

    @Override
    public boolean isEnabled() {
        return userAccount.getIsEnabled();
    }
}
