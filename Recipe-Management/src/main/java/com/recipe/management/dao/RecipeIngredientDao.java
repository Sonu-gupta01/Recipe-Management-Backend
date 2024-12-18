//package com.recipe.management.dao;
//
//
//import com.recipe.management.entity.RecipeIngredients;
//
//public interface RecipeIngredientDao {
//    void save(RecipeIngredients recipeIngredients);
//}


package com.recipe.management.dao;

import com.recipe.management.entity.RecipeIngredients;

/**
 * The RecipeIngredientDao interface provides a method for managing the relationship
 * between recipes and ingredients in the Recipe Management system.
 * It allows saving or updating associations between a recipe and its ingredients.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Save new recipe-ingredient associations</li>
 *   <li>Update existing recipe-ingredient associations</li>
 * </ul>
 *
 * <p>This interface is essential for the Recipe Management module, enabling the system
 * to maintain accurate and flexible associations between recipes and their ingredients.
 *
 * @see com.recipe.management.entity.RecipeIngredients
 */
public interface RecipeIngredientDao {

    /**
     * Saves a new recipe-ingredient association or updates an existing one in the database.
     *
     * @param recipeIngredients the RecipeIngredients entity representing the association between
     *                          a recipe and its ingredients
     */
    void save(RecipeIngredients recipeIngredients);
}
