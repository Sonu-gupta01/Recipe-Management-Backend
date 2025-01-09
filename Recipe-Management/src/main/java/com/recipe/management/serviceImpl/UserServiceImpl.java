package com.recipe.management.serviceImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.dao.UserDao;
import com.recipe.management.dto.UserDTO;
import com.recipe.management.entity.Users;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.exception.DataServiceException;
import com.recipe.management.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDTO doGetUserById(Long id) throws BusinessException {
        try {
            return Optional.ofNullable(userDao.findUserById(id))
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .orElseThrow(() -> new BusinessException(ErrorMessages.USER_NOT_FOUND + id));
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.USER_FETCH_ERROR, e);
        }
    }

    @Override
    public List<UserDTO> doGetAllUsers() throws BusinessException {
        try {
            List<Users> users = userDao.findAllUsers();
            if (users.isEmpty()) {
                throw new BusinessException(ErrorMessages.NO_USERS_FOUND);
            }
            return users.stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.USER_FETCH_ERROR, e);
        }
    }

    @Override
    public void doUpdateUser(Users users) throws BusinessException {
        try {
            if (userDao.findUserById(users.getId()) == null) {
                throw new BusinessException(ErrorMessages.USER_NOT_FOUND + users.getId());
            }
            userDao.saveUser(users);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.USER_SAVE_ERROR, e);
        }
    }

    @Override
    public void deleteUserById(Long id) throws BusinessException {
        try {
            if (userDao.findUserById(id) == null) {
                throw new BusinessException(ErrorMessages.USER_NOT_FOUND + id);
            }
            userDao.deleteUserById(id);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.USER_DELETE_ERROR, e);
        }
    }

    @Override
    public Users doFindByEmailAndPassword(String email, String password) throws BusinessException {
        try {
            Users user = Optional.ofNullable(userDao.findByEmail(email))
                    .orElseThrow(() -> new BusinessException(ErrorMessages.INVALID_CREDENTIALS));
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BusinessException(ErrorMessages.INVALID_CREDENTIALS);
            }
            return user;
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.LOGIN_ERROR, e);
        }
    }

    @Override
    public String doRegisterUser(Users users) throws BusinessException {
        try {
            if (userDao.findByUsername(users.getUsername()) != null) {
                throw new BusinessException(ErrorMessages.USER_ALREADY_EXISTS);
            }
            String hashedPassword = passwordEncoder.encode(users.getPassword());
            users.setPassword(hashedPassword);
            userDao.saveUser(users);
            return ErrorMessages.USER_REGISTERED;
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.USER_SAVE_ERROR, e);
        }
    }

    @Override
    public void doResetPassword(String email, String newPassword) throws BusinessException {
        if (email == null || email.isBlank()) {
            throw new BusinessException(ErrorMessages.EMAIL_EMPTY);
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new BusinessException(ErrorMessages.PASSWORD_EMPTY);
        }
        try {
            Users user = userDao.findByEmail(email);
            if (user == null) {
                throw new BusinessException(ErrorMessages.USER_NOT_FOUND_WITH_EMAIL);
            }
            String hashedPassword = passwordEncoder.encode(newPassword);
            userDao.updatePassword(email, hashedPassword);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.PASSWORD_RESET_ERROR, e);
        }
    }


    @Override
    public Optional<Users> doFindByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BusinessException(ErrorMessages.EMAIL_CANNOT_BE_NULL);
        }
        return userDao.findUserByEmail(email);
    }
}


