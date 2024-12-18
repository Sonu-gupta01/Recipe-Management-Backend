package com.recipe.management.dao;

import com.recipe.management.entity.Menus;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The MenusDao interface defines methods for managing menus in the Recipe Management system.
 * It supports data access operations such as creating, retrieving, deleting, and counting menus.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Retrieve menus by their unique ID</li>
 *   <li>Fetch menus with pagination for efficient display</li>
 *   <li>Count the total number of menus in the system</li>
 *   <li>Save new menus or update existing ones</li>
 *   <li>Delete menus by their ID</li>
 * </ul>
 *
 * <p>This interface is essential for implementing the Personalized Menu Creation module,
 * which allows users to manage meal plans by associating recipes with specific meals.
 */
public interface MenusDao {

    /**
     * Finds a menu by its unique ID.
     * @param id the unique identifier of the menu
     * @return the Menus entity if found
     */
    Menus findMenuById(Long id) throws DataServiceException;

    /**
     * Retrieves a paginated list of all menus in the system.
     */
    List<Menus> findAllMenusWithPagination(int page, int size) throws DataServiceException;

    /**
     * Counts the total number of menus in the system.
     */
    long countTotalMenus() throws DataServiceException;

    /**
     * Saves a new menu or updates an existing one in the database.
     */
    void saveMenu(Menus menu) throws DataServiceException;

    /**
     * Deletes a menu from the database based on its unique ID.
     */
    void deleteMenuById(Long id) throws DataServiceException;

    /**
     * Finds menus scheduled to start within the next 30 minutes from the given current time.
     *
     * <p>This method retrieves a list of menus that are scheduled to begin within the next
     * 30 minutes of the specified {@code currentTime}. The comparison is inclusive, meaning
     * it includes menus that start exactly at {@code currentTime} or within the next 30 minutes.
     * This is useful for scenarios such as notifying users about upcoming menus or preparing
     * resources for imminent menu sessions.
     */
    List<Menus> findMenusWithinNext30Minutes(LocalDateTime currentTime) throws DataServiceException;

    /**
     * Retrieves the menu based on userID.
     */
    List<Menus> findMenusByUserId(Long userId, int page, int size) throws DataServiceException;

    /**
     * Counts the total number of menus associated with a specific user..
     */
    long countMenusByUserId(Long userId) throws DataServiceException;
}

