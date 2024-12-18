
package com.recipe.management.service;

import com.recipe.management.dto.IngredientDTO;
import com.recipe.management.exception.BusinessException;

import java.util.List;

/**
 * Service interface for managing ingredients in the recipe management system.
 * This interface provides methods for retrieving, creating, updating, and deleting ingredients.
 * It also handles the mapping between the Ingredient entity and its corresponding DTO.
 */
public interface IngredientService {
    /**
     * Retrieves an ingredient by its unique ID.
     * @param id The ID of the ingredient to retrieve.
     * @return The ingredient details as an {@link IngredientDTO}.
     */
    IngredientDTO doGetIngredientById(Long id) throws BusinessException;

    /**
     * Retrieves all ingredients in the system.
     *
     * @return A list of {@link IngredientDTO} objects representing all ingredients.
     */
    List<IngredientDTO> doGetAllIngredients() throws BusinessException;

    /**
     * Creates a new ingredient in the system.
     */
    void doCreateIngredient(IngredientDTO ingredientDTO) throws BusinessException;

    /**
     * Updates an existing ingredient by its unique ID.
     */
    void doUpdateIngredient(Long id, IngredientDTO ingredientDTO) throws BusinessException;

    /**
     * Deletes an ingredient by its unique ID.
     */
    void deleteIngredientById(Long id) throws BusinessException;
}
