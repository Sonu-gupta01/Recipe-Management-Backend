package com.recipe.management.daoImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.MenuQueries;
import com.recipe.management.dao.MenusDao;
import com.recipe.management.entity.Menus;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class MenusDaoImpl implements MenusDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Menus findMenuById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Menus.class, id);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MENU_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<Menus> findAllMenusWithPagination(int page, int size) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(MenuQueries.FETCH_ALL_MENUS, Menus.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MENU_PAGINATION_FETCH_ERROR, e);
        }
    }

    @Override
    public long countTotalMenus() throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(MenuQueries.COUNT_TOTAL_MENUS, Long.class).uniqueResult();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MENU_COUNT_ERROR, e);
        }
    }

    @Override
    public void saveMenu(Menus menu) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(menu);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MENU_SAVE_ERROR, e);
        }
    }

    @Override
    public void deleteMenuById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Menus menu = session.get(Menus.class, id);
            if (menu != null) {
                session.delete(menu);
            } else {
                throw new DataServiceException(ErrorMessages.MENU_NOT_FOUND + id);
            }
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MENU_DELETE_ERROR + id, e);
        }
    }

    @Override
    public List<Menus> findMenusWithinNext30Minutes(LocalDateTime currentTime) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(MenuQueries.FETCH_MENUS_WITHIN_NEXT_30_MINUTES, Menus.class)
                    .setParameter(MenuQueries.CURRENT_TIME, currentTime)
                    .setParameter(MenuQueries.THRESOLD_TIME, currentTime.plusMinutes(30))
                    .getResultList();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MENU_FETCH_NEXT_30_MINUTES_ERROR, e);
        }
    }

    @Override
    public List<Menus> findMenusByUserId(Long userId, int page, int size) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(MenuQueries.FETCH_MENUS_BY_USER_ID, Menus.class)
                    .setParameter(MenuQueries.USER_ID, userId)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MENU_FETCH_ERROR + MenuQueries.FOR_USER_ID + userId, e);
        }
    }

    @Override
    public long countMenusByUserId(Long userId) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(MenuQueries.COUNT_MENUS_BY_USER_ID, Long.class)
                    .setParameter(MenuQueries.USER_ID, userId)
                    .uniqueResult();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MENU_COUNT_ERROR + MenuQueries.FOR_USER_ID + userId, e);
        }
    }
}

