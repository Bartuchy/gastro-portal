package com.security.module.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRecipeDto {
    private String name;
    private String category;
    private String description;
    private String ingredients;
    private String directions;
}
