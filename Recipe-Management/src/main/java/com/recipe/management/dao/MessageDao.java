package com.recipe.management.dao;

import com.recipe.management.entity.Messages;
import com.recipe.management.exception.DataAccessException;
import com.recipe.management.exception.DataServiceException;

import java.util.List;

/**
 * The MessageDao interface provides methods to manage messages in the Recipe Management system.
 * It supports the data access layer for functionalities such as sending, retrieving, and deleting
 * messages between users.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Send and save messages</li>
 *   <li>Retrieve all messages or messages exchanged between specific users</li>
 *   <li>Delete messages by their unique ID</li>
 * </ul>
 *
 * <p>This interface is essential for implementing the Chat Module, where users can exchange
 * messages regarding recipes they create or manage.
 */
public interface MessageDao {

    /**
     * Finds a specific message by its unique ID.
     * @param id the unique identifier of the message
     * @return the Messages entity if found
     */
    Messages findMessageById(Long id) throws DataServiceException;

    /**
     * Retrieves all messages in the system.
     * @return a list of all Messages entities
     */
    List<Messages> findAllMessages() throws DataServiceException;

    /**
     * Finds messages exchanged between a specific sender and receiver.
     *
     * @param senderId the unique identifier of the sender
     * @param receiverId the unique identifier of the receiver
     * @return a list of Messages entities exchanged between the specified sender and receiver,
     *         ordered by the time they were sent
     */
    List<Messages> findMessagesBySenderAndReceiver(Long senderId, Long receiverId) throws DataServiceException;

    /**
     * Saves a new message or updates an existing one in the database.
     */
    void saveMessage(Messages messages) throws DataServiceException;

    /**
     * Deletes a message from the database based on its unique ID.
     * @param id the unique identifier of the message to be deleted
     */
    void deleteMessageById(Long id) throws DataServiceException;
}
