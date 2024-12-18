package com.recipe.management.queries;

public class UserQueries {
    public static final String FETCH_ALL_USER = "FROM Users";
    public static final String FETCH_USER_BY_USERNAME = "FROM Users WHERE username = :username";
    public static final String VALIDATE_EMAIL_AND_PASSWORD = "FROM Users WHERE email = :email AND password = :password";
    public static final String FETCH_USER_BY_EMAIL = "FROM Users WHERE email = :email";
}