package com.recipe.management.util;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.dao.IngredientDao;
import com.recipe.management.dto.IngredientDTO;
import com.recipe.management.dto.RecipeDTO;
import com.recipe.management.entity.Ingredients;
import com.recipe.management.entity.RecipeIngredients;
import com.recipe.management.entity.Recipes;
import com.recipe.management.entity.RecipeKind;
import com.recipe.management.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecipeConverter {

    @Autowired
    private IngredientDao ingredientDao;

    public RecipeDTO mapToDTO(Recipes recipe) {
        return RecipeDTO.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .createdBy(recipe.getCreatedBy())
                .prepTimeMinute(recipe.getPrepTimeMinute() != null ? recipe.getPrepTimeMinute() : null)
                .imagePath(recipe.getImagePath())
                .recipeKind(recipe.getRecipeKind().name())
                .ingredients(recipe.getRecipeIngredients().stream()
                        .map(ri -> IngredientDTO.builder()
                                .id(ri.getIngredient().getId())
                                .name(ri.getIngredient().getName())
                                .unit(ri.getIngredient().getUnit())
                                .quantity(ri.getIngredient().getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public Recipes mapToEntity(RecipeDTO recipeDTO) {
        Recipes recipe = new Recipes();
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setCreatedBy(recipeDTO.getCreatedBy());
        recipe.setImagePath(recipeDTO.getImagePath());
        recipe.setPrepTimeMinute(recipeDTO.getPrepTimeMinute() != null
                ? recipeDTO.getPrepTimeMinute() : null);
        recipe.setRecipeKind(RecipeKind.valueOf(recipeDTO.getRecipeKind()));

        recipe.setRecipeIngredients(recipeDTO.getIngredients().stream()
                .map(ingredientDTO -> {
                    Ingredients ingredient = ingredientDao.findIngredientById(ingredientDTO.getId())
                            .orElseThrow(() -> new BusinessException(ErrorMessages.EMAIL_CANNOT_BE_NULL + ingredientDTO.getId()));
                    RecipeIngredients recipeIngredient = new RecipeIngredients();
                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setRecipe(recipe);
                    return recipeIngredient;
                })
                .collect(Collectors.toSet()));
        return recipe;
    }
}
