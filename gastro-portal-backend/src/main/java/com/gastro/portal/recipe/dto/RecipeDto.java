package com.gastro.portal.recipe.dto;


import com.gastro.portal.recipe.RecipeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecipeDto {
    private Long id;
    private LocalDateTime additionDate;
    private String name;
    private String category;
    private String description;
    private String ingredients;
    private String directions;
    private String userName;

    public RecipeDto(RecipeEntity recipeEntity) {
        this.id = recipeEntity.getId();
        this.additionDate = recipeEntity.getDate();
        this.name = recipeEntity.getName();
        this.category = recipeEntity.getCategory();
        this.description = recipeEntity.getDescription();
        this.ingredients = recipeEntity.getIngredients();
        this.directions = recipeEntity.getDirections();
        this.userName = recipeEntity.getUserEntity().getEmail();
    }
}
