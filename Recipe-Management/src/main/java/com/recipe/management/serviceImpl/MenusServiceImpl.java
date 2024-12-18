package com.recipe.management.serviceImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.dao.MenusDao;
import com.recipe.management.dao.RecipeDao;
import com.recipe.management.dto.MenusDTO;
import com.recipe.management.entity.Menus;
import com.recipe.management.entity.Recipes;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.exception.DataServiceException;
import com.recipe.management.service.MenusService;
import com.recipe.management.util.EntityToDTOConverter;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenusServiceImpl implements MenusService {

    @Autowired
    private MenusDao menusDao;

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private EntityToDTOConverter converter;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public MenusDTO doGetMenuById(Long id) throws BusinessException {
        try {
            Menus menu = menusDao.findMenuById(id);
            if (menu == null) {
                throw new BusinessException(ErrorMessages.MENU_NOT_FOUND + id);
            }
            return converter.convertToMenusDTO(menu);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MENU_FETCH_ERROR + id, e);
        }
    }

    @Override
    public Map<String, Object> doGetAllMenusWithPagination(int page, int size) throws BusinessException {
        try {
            List<Menus> menus = menusDao.findAllMenusWithPagination(page, size);
            long totalMenus = menusDao.countTotalMenus();

            Map<String, Object> response = new HashMap<>();
            response.put(ErrorMessages.CONTENT, menus.stream().map(converter::convertToMenusDTO).collect(Collectors.toList()));
            response.put(ErrorMessages.TOTAL_ELEMENT, totalMenus);
            response.put(ErrorMessages.TOTAL_PAGES, (int) Math.ceil((double) totalMenus / size));
            response.put(ErrorMessages.CURRENT_PAGE, page);
            return response;
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MENU_PAGINATION_FETCH_ERROR, e);
        }
    }

    @Override
    public void doCreateMenu(MenusDTO menuDTO) throws BusinessException {
        try {
            List<Recipes> recipes = menuDTO.getRecipeIds().stream()
                    .map(recipeId -> recipeDao.findRecipeById(recipeId)
                            .orElseThrow(() -> new BusinessException(ErrorMessages.RECIPE_NOT_FOUND + recipeId)))
                    .collect(Collectors.toList());
            validateMenuRecipesUniqueness(recipes);
            Menus menu = converter.convertToMenusEntity(menuDTO);
            menu.setRecipes(recipes);
            menusDao.saveMenu(menu);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MENU_SAVE_ERROR, e);
        }
    }

    @Override
    public void doUpdateMenu(Long id, MenusDTO menuDTO) throws BusinessException {
        try {
            Menus existingMenu = menusDao.findMenuById(id);
            if (existingMenu == null) {
                throw new BusinessException(ErrorMessages.MENU_NOT_FOUND + id);
            }
            List<Recipes> recipes = menuDTO.getRecipeIds().stream()
                    .map(recipeId -> recipeDao.findRecipeById(recipeId)
                            .orElseThrow(() -> new BusinessException(ErrorMessages.RECIPE_NOT_FOUND + recipeId)))
                    .collect(Collectors.toList());
            // If the recipes in the updated menu are the same as an existing menu, throw an exception
            if (!existingMenu.getRecipes().equals(new HashSet<>(recipes))) {
                validateMenuRecipesUniqueness(recipes);
            }
            // Update fields
            existingMenu.setMenuName(menuDTO.getMenuName());
            existingMenu.setMealType(menuDTO.getMealType());
            existingMenu.setScheduledAt(menuDTO.getScheduledAt());
            existingMenu.setRecipes(recipes);
            menusDao.saveMenu(existingMenu);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MENU_UPDATE_ERROR + id, e);
        }
    }

    @Override
    public void deleteMenuById(Long id) throws BusinessException {
        try {
            menusDao.deleteMenuById(id);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MENU_DELETE_ERROR + id, e);
        }
    }

    @Override
    public Map<String, Object> doGetMenusForUserWithPagination(Long userId, int page, int size) throws BusinessException {
        try {
            List<Menus> menus = menusDao.findMenusByUserId(userId, page, size);
            long totalMenus = menusDao.countMenusByUserId(userId);
            Map<String, Object> response = new HashMap<>();
            response.put(ErrorMessages.CONTENT, menus.stream().map(converter::convertToMenusDTO).collect(Collectors.toList()));
            response.put(ErrorMessages.TOTAL_ELEMENT, totalMenus);
            response.put(ErrorMessages.TOTAL_PAGES, (int) Math.ceil((double) totalMenus / size));
            response.put(ErrorMessages.CURRENT_PAGE, page);
            return response;
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MENU_PAGINATION_FETCH_ERROR + ErrorMessages.FOR_USER_ID + userId, e);
        }
    }

    /**
     * Validates that a menu with the same recipes does not already exist.
     */
    private void validateMenuRecipesUniqueness(List<Recipes> recipes) throws BusinessException {
        List<Menus> allMenus = menusDao.findAllMenusWithPagination(0, Integer.MAX_VALUE);
        Set<Long> recipeIds = recipes.stream().map(Recipes::getId).collect(Collectors.toSet());
        boolean menuExists = allMenus.stream()
                .anyMatch(menu -> menu.getRecipes().stream()
                        .map(Recipes::getId)
                        .collect(Collectors.toSet())
                        .equals(recipeIds));
        if (menuExists) {
            throw new BusinessException(ErrorMessages.MENU_EXIST_WITH_SAME_RECIPE);
        }
    }
}
