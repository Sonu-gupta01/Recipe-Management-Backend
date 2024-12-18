package com.recipe.management.dao;

import com.recipe.management.entity.Ingredients;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The IngredientDao interface provides methods for managing ingredients in the Recipe Management system.
 * It supports CRUD operations for ingredients, enabling their association with recipes.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Retrieve ingredients by their unique ID</li>
 *   <li>Fetch all ingredients in the system</li>
 *   <li>Save new ingredients to the database</li>
 *   <li>Delete ingredients by their unique ID</li>
 * </ul>
 */
public interface IngredientDao {

    /**
     * Finds an ingredient by its unique ID.
     */
    Optional<Ingredients> findIngredientById(Long id) throws DataServiceException;

    /**
     * Retrieves all ingredients in the system.
     * @return a list of all Ingredients entities
     */
    List<Ingredients> findAllIngredients() throws DataServiceException;

    /**
     * Saves a new ingredient or updates an existing one in the database.
     */
    void saveIngredient(Ingredients ingredient) throws DataServiceException;

    /**
     * Deletes an ingredient from the database based on its unique ID.
     */
    void deleteIngredientById(Long id) throws DataServiceException;
}
