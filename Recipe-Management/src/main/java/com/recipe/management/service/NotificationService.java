package com.recipe.management.service;

import com.recipe.management.dto.MenusDTO;
import com.recipe.management.entity.Recipes;
import com.recipe.management.exception.BusinessException;

/**
 * Service interface for sending notifications to users about scheduled meals.
 * This interface defines the method to send a notification for a menu that includes meal reminders.
 */
public interface NotificationService {

    /**
     * Sends notifications for menus scheduled within the upcoming time frame.
     *
     * <p>This method identifies menus that are scheduled to occur in the near future
     * and sends notifications to the relevant users. The notification process may involve
     * channel like email.
     */
    void doSendNotificationsForUpcomingMenus() throws BusinessException;

    /**
     * Sends a notification when a new recipe is created.
     *
     * <p>This method triggers a notification to inform users about the creation of a new recipe.
     * Notifications sent to  user who created the recipe. The notification includes
     * details about the created recipe, such as its title and description.
     *
     * @param recipe the {@link Recipes} object representing the newly created recipe.
     *               Must not be null and should contain valid recipe information.
     */
    void doSendRecipeCreatedNotification(Recipes recipe) throws BusinessException;
}
