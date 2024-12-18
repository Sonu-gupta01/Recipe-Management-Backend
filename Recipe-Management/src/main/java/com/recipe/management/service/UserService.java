package com.recipe.management.service;

import com.recipe.management.dto.UserDTO;
import com.recipe.management.entity.Users;
import com.recipe.management.exception.BusinessException;

import java.util.List;
import java.util.Optional;

/**
 * The UserService interface defines the business logic for managing users in the Recipe Management system.
 * It provides methods for user registration, authentication, and CRUD operations while ensuring data validation.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Retrieve user details by ID</li>
 *   <li>Fetch all users in the system</li>
 *   <li>Update user details</li>
 *   <li>Delete users by their unique ID</li>
 *   <li>Authenticate users with email and password</li>
 *   <li>Register new users</li>
 * </ul>
 *
 */
public interface UserService {

    /**
     * Retrieves user details by their unique ID.
     */
    UserDTO doGetUserById(Long id) throws BusinessException;

    /**
     * Retrieves all users in the system.
     *
     * @return a list of UserDTO objects containing user details
     */
    List<UserDTO> doGetAllUsers() throws BusinessException;

    /**
     * Updates the details of an existing user.
     */
    void doUpdateUser(Users users) throws BusinessException;

    /**
     * Deletes a user from the system based on their unique ID.
     */
    void deleteUserById(Long id) throws BusinessException;

    /**
     * Authenticates a user based on their email and password.
     */
    Users doFindByEmailAndPassword(String email, String password) throws BusinessException;

    /**
     * Registers a new user in the system.
     */
    String doRegisterUser(Users users) throws BusinessException;

    /**
     * Resets the password for the user associated with the given email address.
     *
     * <p>This method updates the user's password in the system with the provided
     * new password. It ensures that the specified email exists in the system before
     * updating the password. Appropriate error handling is implemented to address
     * issues such as non-existent users or data access failures.
     */
    void doResetPassword(String email, String newPassword) throws BusinessException;

    /**
     * Finds a user by their email address.
     *
     * <p>This method retrieves the user information associated with the given email.
     * If a user with the specified email exists, it returns the user's details
     * encapsulated in an {@link Optional}. If no user is found, the {@link Optional}
     * will be empty.
     */
    Optional<Users> doFindByEmail(String email) throws BusinessException;
}

