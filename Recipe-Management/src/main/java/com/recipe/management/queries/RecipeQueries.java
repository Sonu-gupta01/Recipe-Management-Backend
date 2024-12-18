package com.recipe.management.queries;

public class RecipeQueries {
    public static final String FETCH_RECIPE_BY_RECIPE_KIND= "FROM Recipes WHERE recipeKind = :recipeKind";
    public static final String FETCH_ALL_RECIPE = "FROM Recipes";
    public static final String FETCH_RECIPE_BY_RECIPE_TITLE = "FROM Recipes WHERE title = :title";
    public static final String FETCH_RECIPE_BY_INGREDIENT = "SELECT DISTINCT r FROM Recipes r JOIN r.recipeIngredients ri JOIN ri.ingredient i WHERE i.name = :ingredient";
    public static final String FETCH_RECIPE_BY_TITLE = "FROM Recipes r WHERE LOWER(r.title) LIKE :query";
}
