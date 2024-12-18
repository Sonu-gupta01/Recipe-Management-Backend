package com.recipe.management.daoImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.UserQueries;
import com.recipe.management.dao.UserDao;
import com.recipe.management.entity.Users;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;
import com.recipe.management.exception.DuplicateDataException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Users findUserById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Users.class, id);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.USER_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<Users> findAllUsers() throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(UserQueries.FETCH_ALL_USER, Users.class).list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.USER_FETCH_ERROR, e);
        }
    }

    @Override
    public void saveUser(Users users) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(users);
        } catch (ConstraintViolationException e) {
            throw new DataServiceException(ErrorMessages.DUPLICATE_USER_ERROR + users.getUsername(), e);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.USER_SAVE_ERROR, e);
        }
    }

    @Override
    public void deleteUserById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Users user = session.get(Users.class, id);
            if (user != null) {
                session.delete(user);
            } else {
                throw new DataServiceException(ErrorMessages.USER_NOT_FOUND + id);
            }
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.USER_DELETE_ERROR, e);
        }
    }

    @Override
    public Users findByUsername(String username) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(UserQueries.FETCH_USER_BY_USERNAME, Users.class)
                    .setParameter(ErrorMessages.USERNAME, username)
                    .uniqueResult();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.USER_FETCH_ERROR, e);
        }
    }

    @Override
    public Users findByEmailAndPassword(String email, String password) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(UserQueries.VALIDATE_EMAIL_AND_PASSWORD, Users.class)
                    .setParameter(ErrorMessages.EMAIL, email)
                    .setParameter(ErrorMessages.PASSWORD, password)
                    .uniqueResult();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.LOGIN_ERROR, e);
        }
    }

    @Override
    public Users findByEmail(String email) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(UserQueries.FETCH_USER_BY_EMAIL, Users.class)
                    .setParameter(ErrorMessages.EMAIL, email)
                    .uniqueResult();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.USER_FETCH_ERROR, e);
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Users user = findByEmail(email);
            if (user != null) {
                user.setPassword(newPassword);
                session.update(user);
            } else {
                throw new DataServiceException(ErrorMessages.USER_NOT_FOUND_WITH_EMAIL + email);
            }
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.PASSWORD_UPDATE_ERROR, e);
        }
    }

    @Override
    public Optional<Users> findUserByEmail(String email) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(UserQueries.FETCH_USER_BY_EMAIL, Users.class)
                    .setParameter(ErrorMessages.EMAIL, email)
                    .uniqueResultOptional();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.ERROR_FETCHING_USER_BY_EMIAL + email, e);
        }
    }
}



