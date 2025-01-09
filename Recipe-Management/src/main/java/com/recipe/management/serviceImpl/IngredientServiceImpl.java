package com.recipe.management.serviceImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.dao.IngredientDao;
import com.recipe.management.dto.IngredientDTO;
import com.recipe.management.entity.Ingredients;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.exception.DataServiceException;
import com.recipe.management.service.IngredientService;
import com.recipe.management.util.EntityToDTOConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private EntityToDTOConverter converter;

    @Override
    public IngredientDTO doGetIngredientById(Long id) throws BusinessException {
        try {
            return ingredientDao.findIngredientById(id)
                    .map(converter::mapToDTO)
                    .orElseThrow(() -> new BusinessException(ErrorMessages.INGREDIENT_NOT_FOUND + id));
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.INGREDIENT_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<IngredientDTO> doGetAllIngredients() throws BusinessException {
        try {
            List<Ingredients> ingredients = ingredientDao.findAllIngredients();
            if (ingredients.isEmpty()) {
                throw new BusinessException(ErrorMessages.INGREDIENT_LIST_EMPTY);
            }
            return ingredients.stream()
                    .map(converter::mapToDTO)
                    .collect(Collectors.toList());
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.INGREDIENT_LIST_FETCH_ERROR, e);
        }
    }

    @Override
    public void doCreateIngredient(IngredientDTO ingredientDTO) throws BusinessException {
        try {
            Ingredients ingredient = converter.mapToEntity(ingredientDTO);
            ingredientDao.saveIngredient(ingredient);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.INGREDIENT_SAVE_ERROR, e);
        }
    }

    @Override
    public void doUpdateIngredient(Long id, IngredientDTO ingredientDTO) throws BusinessException {
        try {
            Ingredients existingIngredient = ingredientDao.findIngredientById(id)
                    .orElseThrow(() -> new BusinessException(ErrorMessages.INGREDIENT_NOT_FOUND + id));
            existingIngredient.setName(ingredientDTO.getName());
            existingIngredient.setQuantity(ingredientDTO.getQuantity());
            existingIngredient.setUnit(ingredientDTO.getUnit());
            ingredientDao.saveIngredient(existingIngredient);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.INGREDIENT_UPDATE_ERROR + id, e);
        }
    }

    @Override
    public void deleteIngredientById(Long id) throws BusinessException {
        try {
            ingredientDao.deleteIngredientById(id);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.INGREDIENT_DELETE_ERROR + id, e);
        }
    }
}
