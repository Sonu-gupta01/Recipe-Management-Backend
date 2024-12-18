package com.recipe.management.daoImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.RecipeQueries;
import com.recipe.management.dao.RecipeDao;
import com.recipe.management.entity.Recipes;
import com.recipe.management.entity.RecipeKind;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RecipeDaoImpl implements RecipeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Recipes> findRecipeById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Recipes recipe = session.get(Recipes.class, id);
            return Optional.ofNullable(recipe);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RECIPE_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<Recipes> findByRecipeKind(RecipeKind recipeKind) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Recipes> query = session.createQuery(RecipeQueries.FETCH_RECIPE_BY_RECIPE_KIND, Recipes.class);
            query.setParameter("recipeKind", recipeKind);
            return query.list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RECIPE_KIND_FETCH_ERROR, e);
        }
    }

    @Override
    public List<Recipes> findAllRecipes() throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(RecipeQueries.FETCH_ALL_RECIPE, Recipes.class).list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RECIPE_FETCH_ALL_ERROR, e);
        }
    }

        @Override
        public void saveRecipe(Recipes recipe) throws DataServiceException {
            try {
                Session session = sessionFactory.getCurrentSession();
                session.save(recipe);
            } catch (DataAccessException e) {
                throw new DataServiceException(ErrorMessages.RECIPE_SAVE_ERROR, e);
            }
        }

        @Override
        public void updateRecipe(Recipes recipe) throws DataServiceException {
            try {
                Session session = sessionFactory.getCurrentSession();
                session.update(recipe);
            } catch (DataAccessException e) {
                throw new DataServiceException(ErrorMessages.RECIPE_UPDATE_ERROR, e);
            }
        }

    @Override
    public List<Recipes> findRecipeByTitle(String title) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Recipes> query = session.createQuery(RecipeQueries.FETCH_RECIPE_BY_RECIPE_TITLE, Recipes.class);
            query.setParameter("title", title);
            return query.list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RECIPE_TITLE_FETCH_ERROR, e);
        }
    }

    @Override
    public List<Recipes> findByIngredient(String ingredient) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Recipes> query = session.createQuery(RecipeQueries.FETCH_RECIPE_BY_INGREDIENT, Recipes.class);
            query.setParameter("ingredient", ingredient);
            return query.list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RECIPE_INGREDIENT_FETCH_ERROR, e);
        }
    }

        @Override
        public void deleteRecipeById(Long id) throws DataServiceException {
            try {
                Session session = sessionFactory.getCurrentSession();
                Recipes recipe = session.get(Recipes.class, id);
                if (recipe != null) {
                    session.delete(recipe);
                } else {
                    throw new DataServiceException(ErrorMessages.RECIPE_NOT_FOUND + id);
                }
            } catch (DataAccessException e) {
                throw new DataServiceException(ErrorMessages.RECIPE_DELETE_ERROR, e);
            }
        }

    @Override
    public List<Recipes> searchByTitle(String query) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Recipes> hqlQuery = session.createQuery(RecipeQueries.FETCH_RECIPE_BY_TITLE, Recipes.class);
            hqlQuery.setParameter("query", "%" + query.toLowerCase() + "%");
            return hqlQuery.list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.RECIPE_SEARCH_ERROR, e);
        }
    }
}

