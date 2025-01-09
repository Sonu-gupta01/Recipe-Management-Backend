package com.recipe.management.dao;

import com.recipe.management.entity.Users;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The UserDao interface provides an abstraction layer for performing CRUD operations
 * on user data in the User Management module. This interface is part of the data access
 * layer for managing users in the recipe management system.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>User Registration</li>
 *   <li>User Authentication</li>
 * </ul>
 */
public interface UserDao {
    /**
     * Finds a user by their unique ID.
     */
    Users findUserById(Long id) throws DataServiceException;
    /**
     * Retrieves a list of all users in the system.
     */
    List<Users> findAllUsers() throws DataServiceException;
    /**
     * Saves a new user or updates an existing user in the database.
     */
    void saveUser(Users users) throws DataServiceException;
    /**
     * Deletes a user from the system based on their unique ID.
     */
    void deleteUserById(Long id) throws DataServiceException;
    /**
     * Finds a user by their unique username.
     */
    Users findByUsername(String username) throws DataServiceException;
    /**
     * Finds a user by their email and password for authentication purposes.
     */
    Users findByEmailAndPassword(String email, String password) throws DataServiceException;

    /**
     * Finds a user by their email address.
     *
     * <p>This method searches for a user in the database using the provided email address.
     * It returns the {@code Users} object if a match is found. If no user is found, an exception
     * is thrown. The method is case-sensitive or case-insensitive depending on the underlying
     * database configuration.
     */
    Users findByEmail(String email) throws DataServiceException;

    /**
     * Updates the password of a user identified by their email address.
     *
     * <p>This method allows updating the password for a specific user. The email
     * is used to locate the user, and the provided new password replaces the existing one.
     * This is particularly useful for password reset or change operations.
     */
    void updatePassword(String email, String newPassword) throws DataServiceException;

    /**
     * Finds a user by their email address and returns of the user.
     *
     * <p>This method searches for a user in the database using the provided email address.
     * If a user is found, it returns an {@code Optional} containing the {@code Users} object.
     * If no user is found, it returns an empty {@code Optional}.
     */
    Optional<Users> findUserByEmail(String email) throws DataServiceException;


}

