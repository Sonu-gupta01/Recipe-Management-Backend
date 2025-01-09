package com.recipe.management.controller;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.RestConstants;
import com.recipe.management.dto.LoginRequestDTO;
import com.recipe.management.dto.UserDTO;
import com.recipe.management.entity.Users;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = RestConstants.BASE_URL)
@RequestMapping(RestConstants.USERS_BASE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(RestConstants.USER_BY_ID)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(userService.doGetUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() throws BusinessException {
        return ResponseEntity.ok(userService.doGetAllUsers());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Users users) throws BusinessException {
        String message = userService.doRegisterUser(users);
        Map<String, String> response = new HashMap<>();
        response.put(ErrorMessages.MESSAGE, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(RestConstants.USER_BY_ID)
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Users users) throws BusinessException {
        users.setId(id);
        userService.doUpdateUser(users);
        return ResponseEntity.ok(ErrorMessages.USER_UPDATED);
    }

    @DeleteMapping(RestConstants.USER_BY_ID)
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws BusinessException {
        userService.deleteUserById(id);
        return ResponseEntity.ok(ErrorMessages.USER_DELETED);
    }

    @PostMapping(RestConstants.USER_LOGIN)
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) throws BusinessException {
        Users user = userService.doFindByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put(ErrorMessages.MESSAGE, ErrorMessages.LOGIN_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @PostMapping(RestConstants.USER_RESET_PASSWORD)
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) throws BusinessException {
        String email = request.get(ErrorMessages.EMAIL);
        String newPassword = request.get(ErrorMessages.NEW_PASSWORD);
        userService.doResetPassword(email, newPassword);
        Map<String, String> response = new HashMap<>();
        response.put(ErrorMessages.MESSAGE, ErrorMessages.PASSWORD_UPDATED);
        return ResponseEntity.ok(response);
    }

    @GetMapping(RestConstants.USER_FIND_BY_EMAIL)
    public ResponseEntity<Map<String, Object>> getUserIdByEmail(@RequestParam String email) throws BusinessException {
        Users user = userService.doFindByEmail(email).orElseThrow(() -> new BusinessException(ErrorMessages.USER_NOT_FOUND_WITH_EMAIL + email));
        Map<String, Object> response = new HashMap<>();
        response.put(ErrorMessages.ID, user.getId());
        return ResponseEntity.ok(response);
    }

}


