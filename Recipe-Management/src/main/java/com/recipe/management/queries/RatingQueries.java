package com.recipe.management.queries;

public class RatingQueries {
    public static String  FETCH_ALL_RATING = "FROM Ratings ORDER BY createdAt DESC";
    public static String CALCULATE_AVG_RATING = "SELECT AVG(r.rating) FROM Ratings r WHERE r.recipe.id = :recipeId";
}
