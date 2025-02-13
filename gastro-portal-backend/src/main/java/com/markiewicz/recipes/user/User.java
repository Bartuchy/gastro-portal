package com.markiewicz.recipes.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.markiewicz.recipes.recipe.Recipe;
import com.markiewicz.recipes.role.Role;
import com.markiewicz.recipes.user.dto.UserRegisterDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email
    private String email;

    @Pattern(regexp = "[a-zA-z]{3,}.*")
    private String username;

    @Pattern(regexp = "[a-zA-Z]{4,}[0-9]+")
    private String password;

    private Boolean isEnabled = false;
    private Boolean isNonLocked = false;
    private Boolean isUsing2FA = false;
    private String secret = Base32.random();

    @ManyToOne(targetEntity = Role.class)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    private List<Recipe> recipes = new ArrayList<>();

    public User(UserRegisterDto userRegisterDto, String password) {
        this.email = userRegisterDto.getEmail();
        this.username = userRegisterDto.getUsername();
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
