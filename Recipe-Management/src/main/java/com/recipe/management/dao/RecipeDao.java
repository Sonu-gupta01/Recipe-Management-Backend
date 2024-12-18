package com.recipe.management.dao;

import com.recipe.management.entity.RecipeKind;
import com.recipe.management.entity.Recipes;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The RecipeDao interface provides an abstraction for performing CRUD operations
 * and querying recipes in the Recipe Management module of the application.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Create, read, update, and delete (CRUD) recipes</li>
 *   <li>Search recipes by title, ingredient, or recipe type</li>
 *   <li>Retrieve all recipes or specific recipes by ID</li>
 * </ul>
 *
 * <p>This interface serves as a contract for the data access layer responsible
 * for interacting with the database to manage recipes.
 */
public interface RecipeDao {

    /**
     * Retrieves all recipes in from the database.
     */
    List<Recipes> findAllRecipes() throws DataServiceException;

    /**
     * Saves a new recipe to the database.
     */
    void saveRecipe(Recipes recipe) throws DataServiceException;

    /**
     * Updates an existing recipe in the database.
     */
    void updateRecipe(Recipes recipe) throws DataServiceException;

    /**
     * Finds recipes by their title.
     */
    List<Recipes> findRecipeByTitle(String title) throws DataServiceException;

    /**
     * Finds recipes that use a specific ingredient.
     */
    List<Recipes> findByIngredient(String ingredient) throws DataServiceException;

    /**
     * Finds recipes by their recipe kind (e.g., Vegetarian, NonVegetarian, Vegan,).
     */
    List<Recipes> findByRecipeKind(RecipeKind recipeKind) throws DataServiceException;

    /**
     * Finds a specific recipe by its unique ID.
     */
    Optional<Recipes> findRecipeById(Long id) throws DataServiceException;

    /**
     * Deletes a recipe from the database based on its unique ID.
     *
     */
    void deleteRecipeById(Long id) throws DataServiceException;

    /**
     * Searches for recipes based on the provided title query.
     *
     * <p>This method retrieves a list of recipes whose titles match or partially match
     * the given query string. The search is case-insensitive and can handle partial matches,
     * enabling users to find recipes even if they do not provide the exact title.
     * This method is particularly useful for implementing
     * search functionalities in applications where users want to quickly locate recipes.
     *
     */
    public List<Recipes> searchByTitle(String query) throws DataServiceException;
}

