package com.gastro.portal.recipe.mapper;

import com.gastro.portal.recipe.RecipeEntity;
import com.gastro.portal.recipe.dto.CreateRecipeDto;
import com.gastro.portal.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RecipeMapper {
    private final UserService userService;

    public RecipeEntity createRecipeDtoToRecipeEntity(CreateRecipeDto createRecipeDto) {
        return RecipeEntity.builder()
                .date(LocalDateTime.now())
                .name(createRecipeDto.getName())
                .category(createRecipeDto.getCategory())
                .description(createRecipeDto.getDescription())
                .ingredients(createRecipeDto.getIngredients())
                .directions(createRecipeDto.getDirections())
                .userEntity(userService.getLoggedInUser())
                .build();
    }
}
