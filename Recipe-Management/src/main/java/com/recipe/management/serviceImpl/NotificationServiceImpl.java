package com.recipe.management.serviceImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.dao.MenusDao;
import com.recipe.management.dao.RecipeDao;
import com.recipe.management.dao.UserDao;
import com.recipe.management.entity.Menus;
import com.recipe.management.entity.Recipes;
import com.recipe.management.entity.Users;
import com.recipe.management.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MenusDao menusDao;

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private UserDao usersDao;

    // Notify users about scheduled menus
    @Scheduled(fixedRate = 300000)
    public void doSendNotificationsForUpcomingMenus() {
        List<Menus> upcomingMenus = menusDao.findMenusWithinNext30Minutes(LocalDateTime.now());
        for (Menus menu : upcomingMenus) {
            String userEmail = menu.getUser().getEmail();
            String username = menu.getUser().getUsername();
            String menuName = menu.getMenuName();
            String emailContent = generateMenuReminderContent(username, menuName, menu.getScheduledAt());
            doSendEmail(userEmail, ErrorMessages.MEAL_PREP_REMINDER, emailContent);
        }
    }

    private String generateMenuReminderContent(String username, String menuName, LocalDateTime scheduledAt) {
        Context context = new Context();
        context.setVariable(ErrorMessages.USERNAME, username);
        context.setVariable(ErrorMessages.MENU_NAME, menuName);
        context.setVariable(ErrorMessages.SCHEDULE_AT, scheduledAt);
        return templateEngine.process(ErrorMessages.MENU_REMINDER, context);
    }

    public void doSendRecipeCreatedNotification(Recipes recipe) {
        Users user = usersDao.findUserById(recipe.getCreatedBy());
        if (user != null) {
            String userEmail = user.getEmail();
            String username = user.getUsername();
            String recipeTitle = recipe.getTitle();
            String description = recipe.getDescription();
            double prepTime = recipe.getPrepTimeMinute();
            // Generate email content using Thymeleaf
            String emailContent = generateRecipeCreatedContent(username, recipeTitle, description, prepTime);
            doSendEmail(userEmail,ErrorMessages.RECIPE_CREATED, emailContent);
        } else {
            System.err.println(ErrorMessages.USER_NOT_FOUND_FOR_RECIPE + recipe.getId());
        }
    }

    private String generateRecipeCreatedContent(String username, String recipeTitle, String description, double prepTime) {
        Context context = new Context();
        context.setVariable(ErrorMessages.USERNAME, username);
        context.setVariable(ErrorMessages.RECIPE_TITLE, recipeTitle);
        context.setVariable(ErrorMessages.DESCRIPTION, description);
        context.setVariable(ErrorMessages.PREP_TIME, prepTime);
        return templateEngine.process(ErrorMessages.RECIPE_CREATE, context);
    }

    // send emails
    private void doSendEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
            System.out.println(ErrorMessages.EMAIL_SENT_TO + to);
        } catch (MessagingException e) {
            System.err.println(ErrorMessages.ERROR_EMAIL_SENDING_TO + to + ": " + e.getMessage());
        }
    }
}
