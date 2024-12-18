package com.recipe.management.service;

import com.recipe.management.dto.RatingDTO;
import com.recipe.management.exception.BusinessException;

import java.util.List;

/**
 * The RatingService interface defines the business logic for managing recipe ratings
 * in the Recipe Management system. It provides methods for creating, updating, retrieving,
 * and deleting ratings, as well as validating rating values.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Retrieve ratings by ID or all ratings</li>
 *   <li>Create new ratings</li>
 *   <li>Update existing ratings</li>
 *   <li>Delete ratings by their unique ID</li>
 * </ul>
 *
 * <p>This interface is critical for the Ratings and Comments module, enabling users to
 * provide feedback on recipes with a 1-5 star rating system.
 *
 * @see com.recipe.management.dto.RatingDTO
 * @see com.recipe.management.exception.BusinessException
 */
public interface RatingService {

    /**
     * Retrieves a specific rating by its unique ID.
     */
    RatingDTO doGetRatingById(Long id) throws BusinessException;

    /**
     * Retrieves all ratings in the system.
     * @return a list of RatingDTO objects containing rating details
     */
    List<RatingDTO> doGetAllRatings() throws BusinessException;

    /**
     * Creates a new rating for a recipe.
     */
    void doCreateRating(RatingDTO ratingDTO) throws BusinessException;

    /**
     * Updates an existing rating in the system.
     * @param id the unique identifier of the rating to be updated
     * @param ratingDTO the RatingDTO containing the updated rating details
     */
    void doUpdateRating(Long id, RatingDTO ratingDTO) throws BusinessException;

    /**
     * Deletes a rating from the system based on its unique ID.
     */
    void deleteRatingById(Long id) throws BusinessException;

    /**
     * Calculate the average rating of a recipe based on the rating created by users.
     * sum of all the rating divided by number of user who rate that recipe
     */
    Double doGetAverageRatingForRecipe(Long recipeId) throws BusinessException;
}

