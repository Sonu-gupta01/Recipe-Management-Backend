package com.recipe.management.serviceImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.dao.RecipeDao;
import com.recipe.management.dao.IngredientDao;
import com.recipe.management.dto.RecipeDTO;
import com.recipe.management.entity.Ingredients;
import com.recipe.management.entity.RecipeIngredients;
import com.recipe.management.entity.Recipes;
import com.recipe.management.entity.RecipeKind;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.exception.DataServiceException;
import com.recipe.management.service.NotificationService;
import com.recipe.management.service.RecipeService;
import com.recipe.management.util.RecipeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private RecipeConverter recipeConverter;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<RecipeDTO> doGetAllRecipes() throws BusinessException {
        try {
            return recipeDao.findAllRecipes().stream()
                    .map(recipeConverter::mapToDTO)
                    .collect(Collectors.toList());
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RECIPE_FETCH_ALL_ERROR, e);
        }
    }

    @Override
    public RecipeDTO doGetRecipeById(Long id) throws BusinessException {
        try {
            Recipes recipe = recipeDao.findRecipeById(id)
                    .orElseThrow(() -> new BusinessException(ErrorMessages.RECIPE_NOT_FOUND + id));
            return recipeConverter.mapToDTO(recipe);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RECIPE_FETCH_ERROR + id, e);
        }
    }

    @Override
    public void doCreateRecipe(RecipeDTO recipeDTO) throws BusinessException {
        try {
            Recipes recipe = recipeConverter.mapToEntity(recipeDTO);
            validateRecipe(recipe);
            recipeDao.saveRecipe(recipe);
            notificationService.doSendRecipeCreatedNotification(recipe);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RECIPE_SAVE_ERROR, e);
        }
    }

    @Override
    public void doUpdateRecipe(Long id, RecipeDTO recipeDTO, Long userId) throws BusinessException {
        try {
            Recipes recipe = recipeDao.findRecipeById(id)
                    .orElseThrow(() -> new BusinessException(ErrorMessages.RECIPE_NOT_FOUND + id));
            if (!recipe.getCreatedBy().equals(userId)) {
                throw new BusinessException(ErrorMessages.RECIPE_MODIFY_ACCESS);
            }
            recipe.setTitle(recipeDTO.getTitle());
            recipe.setDescription(recipeDTO.getDescription());
            recipe.setPrepTimeMinute(recipeDTO.getPrepTimeMinute());
            recipe.setImagePath(recipeDTO.getImagePath());
            recipe.setRecipeKind(RecipeKind.valueOf(recipeDTO.getRecipeKind()));

            if (recipeDTO.getImagePath() != null && !recipeDTO.getImagePath().isEmpty()) {
                recipe.setImagePath(recipeDTO.getImagePath());
            }
            Set<RecipeIngredients> newIngredients = recipeDTO.getIngredients().stream()
                    .map(ingredientDTO -> {
                        Ingredients ingredient = ingredientDao.findIngredientById(ingredientDTO.getId())
                                .orElseThrow(() -> new BusinessException(
                                        ErrorMessages.INGREDIENT_NOT_FOUND + ingredientDTO.getId()));
                        RecipeIngredients recipeIngredient = new RecipeIngredients();
                        recipeIngredient.setIngredient(ingredient);
                        recipeIngredient.setRecipe(recipe);
                        return recipeIngredient;
                    })
                    .collect(Collectors.toSet());
            recipe.getRecipeIngredients().clear();
            recipe.getRecipeIngredients().addAll(newIngredients);
            recipeDao.updateRecipe(recipe);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RECIPE_UPDATE_ERROR + id, e);
        }
    }


    @Override
    public void deleteRecipeById(Long id, Long userId) throws BusinessException {
        try {
            Recipes recipe = recipeDao.findRecipeById(id)
                    .orElseThrow(() -> new BusinessException(ErrorMessages.RECIPE_NOT_FOUND + id));
            if (!recipe.getCreatedBy().equals(userId)) {
                throw new BusinessException(ErrorMessages.RECIPE_DELETE_ACCESS);
            }
            recipeDao.deleteRecipeById(id);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RECIPE_DELETE_ERROR + id, e);
        }
    }

    @Override
    public List<RecipeDTO> doGetRecipesByRecipeKind(RecipeKind recipeKind) throws BusinessException {
        try {
            return recipeDao.findByRecipeKind(recipeKind).stream()
                    .map(recipeConverter::mapToDTO)
                    .collect(Collectors.toList());
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.RECIPE_FETCH_BY_KIND_ERROR, e);
        }
    }

    @Override
    public List<RecipeDTO> doSearchRecipes(String query) throws BusinessException {
        try {
            List<Recipes> recipes = recipeDao.searchByTitle(query);
            return recipes.stream()
                    .map(recipeConverter::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.ERROR_SEARCH_RESULT + query, e);
        }
    }


    /**
     * Validates the provided recipe to ensure all necessary fields are populated.
     *
     */
    private void validateRecipe(Recipes recipe) throws BusinessException {
        if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
            throw new BusinessException(ErrorMessages.RECIPE_TITLE_EMPTY);
        }
        if (recipe.getRecipeKind() == null) {
            throw new BusinessException(ErrorMessages.RECIPE_KIND_EMPTY);
        }
        if (recipe.getRecipeIngredients() == null || recipe.getRecipeIngredients().isEmpty()) {
            throw new BusinessException(ErrorMessages.RECIPE_INGREDIENTS_EMPTY);
        }
    }
}

