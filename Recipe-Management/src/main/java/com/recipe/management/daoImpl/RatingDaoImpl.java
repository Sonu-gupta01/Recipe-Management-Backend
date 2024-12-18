package com.recipe.management.daoImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.RatingQueries;
import com.recipe.management.dao.RatingDao;
import com.recipe.management.entity.Ratings;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class RatingDaoImpl implements RatingDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Ratings findById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Ratings.class, id);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RATING_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<Ratings> findAll() throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(RatingQueries.FETCH_ALL_RATING, Ratings.class).list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RATING_FETCH_ALL_ERROR, e);
        }
    }

    @Override
    public void save(Ratings rating) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(rating);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RATING_SAVE_ERROR, e);
        }
    }

    @Override
    public void deleteById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Ratings rating = session.get(Ratings.class, id);
            if (rating != null) {
                session.delete(rating);
            } else {
                throw new DataServiceException(ErrorMessages.RATING_NOT_FOUND + id);
            }
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RATING_DELETE_ERROR + id, e);
        }
    }

    @Override
    public Double findAverageRatingForRecipe(Long recipeId) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(RatingQueries.CALCULATE_AVG_RATING, Double.class)
                    .setParameter("recipeId", recipeId)
                    .uniqueResult();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RATING_AVG_ERROR + recipeId, e);
        }
    }
}

