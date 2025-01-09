package com.recipe.management.service;

import com.recipe.management.dto.RecipeDTO;
import com.recipe.management.entity.RecipeKind;
import com.recipe.management.exception.BusinessException;

import java.util.List;

/**
 * The RecipeService interface defines the business logic for managing recipes
 * in the Recipe Management system. It provides methods for creating, updating,
 * retrieving, and deleting recipes, as well as filtering recipes by specific criteria.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Retrieve all recipes or a specific recipe by ID</li>
 *   <li>Create new recipes</li>
 *   <li>Update existing recipes</li>
 *   <li>Delete recipes by their unique ID</li>
 *   <li>Filter recipes by their kind (e.g., Vegetarian, Non-vegetarian, Vegan)</li>
 * </ul>
 *
 * <p>This interface is central to the Recipe Management module, enabling end-users
 * to create, manage, and search for recipes based on their preferences.
 */
public interface RecipeService {

    /**
     * Retrieves all recipes from the database which is created by registered user.
     */
    List<RecipeDTO> doGetAllRecipes() throws BusinessException;

    /**
     * Retrieves a specific recipe by its unique ID.
     */
    RecipeDTO doGetRecipeById(Long id) throws BusinessException;

    /**
     * Creates a new recipe with all the details of the recipe.
     */
    void doCreateRecipe(RecipeDTO recipeDTO) throws BusinessException;

    /**
     * Updates an existing recipe in the database.
     */
    void doUpdateRecipe(Long id, RecipeDTO recipeDTO, Long userId) throws BusinessException;

    /**
     * Deletes a recipe from the database on its unique ID.
     */
    void deleteRecipeById(Long id, Long userId) throws BusinessException;

    /**
     * Retrieves recipes filtered by their kind (e.g., Vegetarian,NonVegetarian, Vegan, etc.).
     */
    List<RecipeDTO> doGetRecipesByRecipeKind(RecipeKind recipeKind) throws BusinessException;

    /**
     * Searches for recipes that match the given query.
     *
     * <p>This method performs a search for recipes based on the specified query string.
     * The search may match recipe titles, descriptions, or other relevant fields as
     * defined by the application's business logic. The results are returned as a list
     * of {@link RecipeDTO} objects, containing the essential details of the recipes.
     */
    public List<RecipeDTO> doSearchRecipes(String query) throws BusinessException;
}
