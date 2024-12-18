package com.recipe.management.queries;

public class RestConstants {

    public static final String BASE_URL = "http://localhost:4200";
    public static final String BASE_API = "/api";

    public static final String INGREDIENTS_BASE = BASE_API + "/ingredients";
    public static final String INGREDIENT_BY_ID = "/{id}";

    public static final String MENUS_BASE = BASE_API + "/menus";
    public static final String MENU_BY_ID = "/{id}";
    public static final String USER_MENU = "/usermenu";

    public static final String MESSAGES_BASE = BASE_API + "/messages";
    public static final String MESSAGE_BY_ID = "/{id}";
    public static final String MESSAGES_BETWEEN_USERS = "/{senderId}/{receiverId}";

    public static final String RATINGS_BASE = BASE_API + "/ratings";
    public static final String RATING_BY_ID = "/{id}";
    public static final String AVERAGE_RATING = "/average/{recipeId}";

    public static final String RECIPES_BASE = BASE_API + "/recipes";
    public static final String RECIPE_CREATE = "/create";
    public static final String RECIPE_BY_ID = "/{id}";
    public static final String RECIPE_SEARCH_BY_KIND = "/searchByKind";
    public static final String RECIPE_SEARCH = "/search";

    public static final String USERS_BASE = BASE_API + "/users";
    public static final String USER_BY_ID = "/{id}";
    public static final String USER_LOGIN = "/login";
    public static final String USER_RESET_PASSWORD = "/reset-password";
    public static final String USER_FIND_BY_EMAIL = "/findByEmail";



}
