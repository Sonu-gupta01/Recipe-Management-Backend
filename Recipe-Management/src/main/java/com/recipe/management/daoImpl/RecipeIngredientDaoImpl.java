package com.recipe.management.daoImpl;

import com.recipe.management.dao.RecipeIngredientDao;

import com.recipe.management.entity.RecipeIngredients;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeIngredientDaoImpl implements RecipeIngredientDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(RecipeIngredients recipeIngredients) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(recipeIngredients);
    }
}
