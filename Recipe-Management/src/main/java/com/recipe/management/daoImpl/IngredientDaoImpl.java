package com.recipe.management.daoImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.IngredientQueries;
import com.recipe.management.dao.IngredientDao;
import com.recipe.management.entity.Ingredients;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class IngredientDaoImpl implements IngredientDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Ingredients> findIngredientById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return Optional.ofNullable(session.get(Ingredients.class, id));
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.INGREDIENT_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<Ingredients> findAllIngredients() throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(IngredientQueries.FETCH_ALL_INGREDIENTS, Ingredients.class).list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.INGREDIENT_LIST_FETCH_ERROR, e);
        }
    }

    @Override
    public void saveIngredient(Ingredients ingredient) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(ingredient);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.INGREDIENT_SAVE_ERROR, e);
        }
    }

    @Override
    public void deleteIngredientById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Ingredients ingredient = session.get(Ingredients.class, id);
            if (ingredient != null) {
                session.delete(ingredient);
            } else {
                throw new DataServiceException(ErrorMessages.INGREDIENT_NOT_FOUND + id);
            }
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.INGREDIENT_DELETE_ERROR + id, e);
        }
    }
}
