package com.recipe.management.queries;

public class MenuQueries {

    public static final String USER_ID = "userId";
    public static final String FOR_USER_ID= " for userId: ";
    public static final String CURRENT_TIME = "currentTime";
    public static final String THRESOLD_TIME = "timeThreshold";

    public static final String FETCH_ALL_MENUS = "FROM Menus";
    public static final String COUNT_TOTAL_MENUS = "SELECT COUNT(m) FROM Menus m";
    public static final String FETCH_MENUS_BY_USER_ID = "FROM Menus m WHERE m.user.id = :userId";
    public static final String COUNT_MENUS_BY_USER_ID = "SELECT COUNT(m) FROM Menus m WHERE m.user.id = :userId";
    public static final String FETCH_MENUS_WITHIN_NEXT_30_MINUTES =
            "FROM Menus m WHERE m.scheduledAt BETWEEN :currentTime AND :timeThreshold";


}
