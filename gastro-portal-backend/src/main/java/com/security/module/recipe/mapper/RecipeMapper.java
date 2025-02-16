package com.security.module.recipe.mapper;

import com.security.module.recipe.Recipe;
import com.security.module.recipe.dto.CreateRecipeDto;
import com.security.module.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RecipeMapper {
    private final UserService userService;

    public Recipe createRecipeDtoToRecipeEntity(CreateRecipeDto createRecipeDto) {
        return Recipe.builder()
                .date(LocalDateTime.now())
                .name(createRecipeDto.getName())
                .category(createRecipeDto.getCategory())
                .description(createRecipeDto.getDescription())
                .ingredients(createRecipeDto.getIngredients())
                .directions(createRecipeDto.getDirections())
                .user(userService.getLoggedInUser())
                .build();
    }
}
