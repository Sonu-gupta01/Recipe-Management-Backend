package com.recipe.management.dao;

import com.recipe.management.entity.Ratings;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;

import java.util.List;

/**
 * The RatingDao interface defines data access operations for managing ratings
 * within the Recipe Management system. It provides methods for performing CRUD
 * operations on ratings and serves as the primary abstraction layer for interacting
 * with the database for rating-related functionalities.
 * <p>This interface is a key component in supporting the Ratings and Comments module,
 * where users can rate recipes (1-5) and manage their feedback.
 */
public interface RatingDao {

    /**
     * Finds a rating by its unique ID.
     */
    Ratings findById(Long id) throws DataServiceException;

    /**
     * Retrieves all ratings in the system, ordered by their creation timestamp in descending order.
     */
    List<Ratings> findAll() throws DataServiceException;

    /**
     * Saves a new rating or updates an existing one in the database.
     */
    void save(Ratings rating) throws DataServiceException;

    /**
     * Deletes a rating from the database based on its unique ID.
     */
    void deleteById(Long id) throws DataServiceException;

    /**
     * Calculates the average rating for a specified recipe.
     */
    Double findAverageRatingForRecipe(Long recipeId) throws DataServiceException;
}

