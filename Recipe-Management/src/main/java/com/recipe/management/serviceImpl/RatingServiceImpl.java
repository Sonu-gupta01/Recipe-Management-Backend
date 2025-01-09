package com.recipe.management.serviceImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.dao.RatingDao;
import com.recipe.management.dao.RecipeDao;
import com.recipe.management.dao.UserDao;
import com.recipe.management.dto.RatingDTO;
import com.recipe.management.entity.Ratings;
import com.recipe.management.entity.Recipes;
import com.recipe.management.entity.Users;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.exception.DataServiceException;
import com.recipe.management.service.RatingService;
import com.recipe.management.util.EntityToDTOConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private EntityToDTOConverter converter;

    @Override
    public RatingDTO doGetRatingById(Long id) throws BusinessException {
        try {
            Ratings rating = ratingDao.findById(id);
            if (rating == null) {
                throw new BusinessException(ErrorMessages.RATING_NOT_FOUND + id);
            }
            return converter.convertToRatingDTO(rating);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RATING_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<RatingDTO> doGetAllRatings() throws BusinessException {
        try {
            return ratingDao.findAll().stream()
                    .map(converter::convertToRatingDTO)
                    .collect(Collectors.toList());
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RATING_FETCH_ALL_ERROR, e);
        }
    }

    @Override
    public void doCreateRating(RatingDTO ratingDTO) throws BusinessException {
        try {
            validateRating(ratingDTO);
            Ratings rating = converter.convertToRatingEntity(ratingDTO);
            rating.setRecipe(recipeDao.findRecipeById(ratingDTO.getRecipeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessages.RECIPE_NOT_FOUND + ratingDTO.getRecipeId())));
            rating.setUser(Optional.ofNullable(userDao.findUserById(ratingDTO.getUserId()))
                    .orElseThrow(() -> new BusinessException(ErrorMessages.USER_NOT_FOUND + ratingDTO.getUserId())));
            ratingDao.save(rating);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RATING_SAVE_ERROR, e);
        }
    }

    @Override
    public void doUpdateRating(Long id, RatingDTO ratingDTO) throws BusinessException {
        try {
            Ratings existingRating = ratingDao.findById(id);
            if (existingRating == null) {
                throw new BusinessException(ErrorMessages.RATING_NOT_FOUND + id);
            }
            validateRating(ratingDTO);
            existingRating.setRating(Long.valueOf(ratingDTO.getRating()));
            ratingDao.save(existingRating);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RATING_UPDATE_ERROR + id, e);
        }
    }

    @Override
    public void deleteRatingById(Long id) throws BusinessException {
        try {
            ratingDao.deleteById(id);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RATING_DELETE_ERROR + id, e);
        }
    }

    @Override
    public Double doGetAverageRatingForRecipe(Long recipeId) throws BusinessException {
        try {
            return Optional.ofNullable(ratingDao.findAverageRatingForRecipe(recipeId)).orElse(0.0);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RATING_AVG_ERROR + recipeId, e);
        }
    }

    private void validateRating(RatingDTO ratingDTO) throws BusinessException {
        if (ratingDTO.getRating() < 1 || ratingDTO.getRating() > 5) {
            throw new BusinessException(ErrorMessages.INVALID_RATING_VALUE);
        }
    }
}