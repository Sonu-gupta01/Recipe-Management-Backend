package com.recipe.management.util;

import com.recipe.management.dto.*;
import com.recipe.management.entity.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityToDTOConverter {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Converts Rating entity to RatingDTO.
     */

    public RatingDTO convertToRatingDTO(Ratings rating) {
        return RatingDTO.builder()
                .id(rating.getId())
                .recipeId(rating.getRecipe().getId())
                .userId(rating.getUser().getId())
                .rating(rating.getRating().intValue()) // Convert Long to Integer
                .createdAt(rating.getCreatedAt())
                .build();
    }

    /**
     * Converts RatingDTO to Rating entity.
     */
    public Ratings convertToRatingEntity(RatingDTO dto) {
        Ratings rating = new Ratings();
        rating.setId(dto.getId());
        rating.setRating(Long.valueOf(dto.getRating())); // Convert Integer to Long
        return rating;
    }

    /**
     * Converts MessageDTO to Message entity.
     */

    public Messages convertToMessageEntity(MessageDTO messageDTO) {
        Messages message = new Messages();
        message.setId(messageDTO.getId());
        message.setMessageText(messageDTO.getMessageText());
        return message;
    }

    public MessageDTO convertToMessageDTO(Messages message) {
        return MessageDTO.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .receiverId(message.getReceiver().getId())
                .recipeId(message.getRecipe() != null ? message.getRecipe().getId() : null)
                .messageText(message.getMessageText())
                .sentAt(message.getSentOn())
                .build();
    }


    /**
     * Converts Menus entity to MenusDTO.
     */

    public MenusDTO convertToMenusDTO(Menus menu) {
        return MenusDTO.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .mealType(menu.getMealType())
                .scheduledAt(menu.getScheduledAt())
                .userId(menu.getUser().getId())
                .recipeIds(menu.getRecipes().stream().map(Recipes::getId).collect(Collectors.toList()))
                .recipeTitles(menu.getRecipes().stream().map(Recipes::getTitle).collect(Collectors.toList()))
                .build();
    }

    /**
     * Converts MenusDTO to Menus entity.
     */

    public Menus convertToMenusEntity(MenusDTO dto) {
        Menus menu = new Menus();
        menu.setId(dto.getId());
        menu.setMenuName(dto.getMenuName());
        menu.setMealType(dto.getMealType());
        menu.setScheduledAt(dto.getScheduledAt());
        Users user = sessionFactory.getCurrentSession().get(Users.class, dto.getUserId());
        menu.setUser(user);
        List<Recipes> recipes = dto.getRecipeIds().stream()
                .map(recipeId -> sessionFactory.getCurrentSession().get(Recipes.class, recipeId))
                .collect(Collectors.toList());
        menu.setRecipes(recipes);

        return menu;
    }

    /**
     * Converts Recipes entity to RecipeDTO.
     */
    public RecipeDTO convertToRecipeDTO(Recipes recipe) {
        return RecipeDTO.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .prepTimeMinute(recipe.getPrepTimeMinute() != null ? recipe.getPrepTimeMinute() : null)
                .imagePath(recipe.getImagePath())
                .recipeKind(recipe.getRecipeKind().name())
                .ingredients(recipe.getRecipeIngredients().stream()
                        .map(recipeIngredient -> IngredientDTO.builder()
                                .id(recipeIngredient.getIngredient().getId())
                                .name(recipeIngredient.getIngredient().getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Converts Ingredient entity to IngredientDTO.
     */
    public IngredientDTO convertToIngredientDTO(Ingredients ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .quantity(ingredient.getQuantity())
                .unit(ingredient.getUnit())
                .build();
    }
    /**
     * Converts RecipeIngredients entity to IngredientDTO.
     */
    public IngredientDTO convertToRecipeIngredientDTO(RecipeIngredients recipeIngredient) {
        return IngredientDTO.builder()
                .id(recipeIngredient.getIngredient().getId())
                .name(recipeIngredient.getIngredient().getName())
                .build();
    }

    public IngredientDTO mapToDTO(Ingredients ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .quantity(ingredient.getQuantity())
                .unit(ingredient.getUnit())
                .build();
    }

    public Ingredients mapToEntity(IngredientDTO ingredientDTO) {
        return Ingredients.builder()
                .id(ingredientDTO.getId())
                .name(ingredientDTO.getName())
                .quantity(ingredientDTO.getQuantity())
                .unit(ingredientDTO.getUnit())
                .build();
    }
}

