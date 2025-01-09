package com.recipe.management.daoImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.MessageQueries;
import com.recipe.management.dao.MessageDao;
import com.recipe.management.entity.Messages;
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
public class MessageDaoImpl implements MessageDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Messages findMessageById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Messages.class, id);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MESSAGE_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<Messages> findAllMessages() throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(MessageQueries.FETCH_ALL_MESSAGE, Messages.class).list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MESSAGE_FETCH_ALL_ERROR, e);
        }
    }

    @Override
    public List<Messages> findMessagesBySenderAndReceiver(Long senderId, Long receiverId) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(MessageQueries.FETCH_MESSAGE_BETWEEN_SENDER_AND_RECEIVER, Messages.class)
                    .setParameter(MessageQueries.SENDER_ID, senderId)
                    .setParameter(MessageQueries.RECEIVER_ID, receiverId)
                    .list();
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MESSAGE_FETCH_BETWEEN_USERS_ERROR, e);
        }
    }

    @Override
    public void saveMessage(Messages messages) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(messages);
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MESSAGE_SAVE_ERROR, e);
        }
    }

    @Override
    public void deleteMessageById(Long id) throws DataServiceException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Messages message = session.get(Messages.class, id);
            if (message != null) {
                session.delete(message);
            } else {
                throw new DataServiceException(ErrorMessages.MESSAGE_NOT_FOUND + id);
            }
        } catch (DataAccessException e) {
            throw new DataServiceException(ErrorMessages.MESSAGE_DELETE_ERROR + id, e);
        }
    }
}
