package com.gastro.portal.recipe;

import com.gastro.portal.recipe.exception.ForbiddenException;
import com.gastro.portal.recipe.exception.RecipeNotFoundException;
import com.gastro.portal.recipe.dto.CreateRecipeDto;
import com.gastro.portal.recipe.dto.RecipeDto;
import com.gastro.portal.recipe.mapper.RecipeMapper;
import com.gastro.portal.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final RecipeMapper recipeMapper;

    public List<RecipeDto> getAllRecipes() {
        List<RecipeEntity> recipeEntities = recipeRepository.findAll();
        return recipeEntities.stream()
                .map(RecipeDto::new)
                .toList();
    }

    public RecipeDto getRecipeById(Long id) {
        RecipeEntity recipeEntity = recipeRepository
                .findById(id)
                .orElseThrow(RecipeNotFoundException::new);
        return new RecipeDto(recipeEntity);
    }

    public List<RecipeDto> getRecipesAddedByUser(String username) {
        return recipeRepository
                .findRecipesAddedByUser(username)
                .orElseThrow(RecipeNotFoundException::new)
                .stream()
                .map(RecipeDto::new)
                .toList();

    }

    public List<RecipeEntity> getRecipesWithNameOrFromCategory(String name, String category) {
        if (name == null ^ category == null) {
            if (name != null) {
                return recipeRepository
                        .findByNameContainingIgnoreCaseOrderByDateDesc(name)
                        .orElseThrow(RecipeNotFoundException::new);
            }
            return recipeRepository
                    .findByCategoryContainingIgnoreCaseOrderByDateDesc(category)
                    .orElseThrow(RecipeNotFoundException::new);
        }
        throw new RecipeNotFoundException();
    }

    public RecipeEntity addNewRecipe(CreateRecipeDto createRecipeDto) {
        RecipeEntity recipeEntity = recipeMapper.createRecipeDtoToRecipeEntity(createRecipeDto);
        return recipeRepository.save(recipeEntity);
    }

    @Transactional
    public void updateRecipe(Long id, RecipeEntity recipeEntity) {
        RecipeEntity updatedRecipeEntity = recipeRepository
                .findById(id)
                .orElseThrow(RecipeNotFoundException::new);

        if (checkCompatibility(updatedRecipeEntity)) {
            updateRecipeFields(updatedRecipeEntity, recipeEntity);
        } else {
            throw new ForbiddenException();
        }
    }

    private void updateRecipeFields(RecipeEntity updatedRecipeEntity, RecipeEntity recipeEntity) {
        updatedRecipeEntity.setName(recipeEntity.getName());
        updatedRecipeEntity.setCategory(recipeEntity.getCategory());
        updatedRecipeEntity.setDescription(recipeEntity.getDescription());
        updatedRecipeEntity.setIngredients(recipeEntity.getIngredients());
        updatedRecipeEntity.setDirections(recipeEntity.getDirections());
        updatedRecipeEntity.setDate(LocalDateTime.now());
    }

    public void removeRecipeById(Long id) {
        RecipeEntity recipeEntity = recipeRepository
                .findById(id)
                .orElseThrow(RecipeNotFoundException::new);

        if (checkCompatibility(recipeEntity)) {
            recipeRepository.delete(recipeEntity);
        } else {
            throw new ForbiddenException();
        }
    }

    private boolean checkCompatibility(RecipeEntity recipeEntity) {
        Long loggedInUserId = userService.getLoggedInUser().getId();
        Long recipeAuthorId = recipeEntity.getAuthor().getId();
        return Objects.equals(loggedInUserId, recipeAuthorId);
    }

    public List<String> getAllCategories() {
        return recipeRepository.findAll()
                .stream()
                .map(RecipeEntity::getCategory)
                .toList();
    }
}
