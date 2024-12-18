package com.recipe.management.service;

import com.recipe.management.dto.MenusDTO;
import com.recipe.management.exception.BusinessException;

import java.util.Map;

/**
 * Interface for Menu service operations.
 * This interface defines the core methods for interacting with menus in the recipe management system.
 * It includes CRUD operations for managing menus, as well as pagination support for retrieving multiple menus.
 */
public interface MenusService {
    /**
     * Retrieves a menu by its unique ID.
     */
    MenusDTO doGetMenuById(Long id) throws BusinessException;

    /**
     * Retrieves all menus with pagination support.
     * @return A map containing the list of menus in the 'content' key, total elements in the 'totalElements' key,
     *         total pages in the 'totalPages' key, and current page in the 'currentPage' key.
     */
    Map<String, Object> doGetAllMenusWithPagination(int page, int size) throws BusinessException;

    /**
     * Creates a new menu in the system.
     * @param menuDTO The menu data to be created, represented as a {@link MenusDTO} object.
     */
    void doCreateMenu(MenusDTO menuDTO) throws BusinessException;

    /**
     * Updates an existing menu by its unique ID.
     * @param menusDTO The updated menu data.
     */
    void doUpdateMenu(Long id, MenusDTO menusDTO) throws BusinessException;

    /**
     * Deletes a menu by its unique ID.
     */
    void deleteMenuById(Long id) throws BusinessException;

    /**
     * This method is used to fetch menu for the particular user based on userID.
     */
    Map<String, Object> doGetMenusForUserWithPagination(Long userId, int page, int size) throws BusinessException;
}

