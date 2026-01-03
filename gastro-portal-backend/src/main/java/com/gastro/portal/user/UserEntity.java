package com.gastro.portal.user;

import com.gastro.portal.account.UserAccountEntity;
import com.gastro.portal.recipe.RecipeEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private String displayName;
    private String avatarUrl;
    private String bio;

    @OneToOne(mappedBy = "user")
    private UserAccountEntity userAccount;

    @OneToMany(mappedBy = "author")
    private List<RecipeEntity> recipes = new ArrayList<>();
}
