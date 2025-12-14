package com.gastro.portal.user;

import com.gastro.portal.recipe.RecipeEntity;
import com.gastro.portal.role.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.jboss.aerogear.security.otp.api.Base32;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    @Email
    private String username;
    private String password;
    private Boolean isEnabled;
    private Boolean isNonLocked;
    private Boolean isUsing2FA;
    private String secret = Base32.random();

    @OneToMany(mappedBy = "userEntity")
    private List<RecipeEntity> recipeEntities = new ArrayList<>();


    @ManyToOne(targetEntity = RoleEntity.class)
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
}
