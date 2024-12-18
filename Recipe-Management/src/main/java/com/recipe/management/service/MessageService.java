package com.recipe.management.service;

import com.recipe.management.dto.MessageDTO;
import com.recipe.management.exception.BusinessException;

import java.util.List;

/**
 * The MessageService interface defines the business logic for managing messages
 * in the Recipe Management system. It provides methods for creating, updating,
 * retrieving, and deleting messages, as well as fetching messages exchanged
 * between specific users.
 *
 * <p>Functionalities supported:
 * <ul>
 *   <li>Retrieve messages by their unique ID</li>
 *   <li>Fetch all messages in the system</li>
 *   <li>Fetch messages exchanged between a sender and receiver</li>
 *   <li>Create, update, and delete messages</li>
 * </ul>
 *
 * <p>This interface is essential for implementing the Chat Module, where users
 * can communicate about specific recipes via direct messaging.
 */
public interface MessageService {

    /**
     * Retrieves a specific message by its unique ID.
     *
     * @param id the unique identifier of the message
     * @return a MessageDTO containing the message details
     */
    MessageDTO doGetMessageById(Long id) throws BusinessException;

    /**
     * Retrieves all messages in the system.
     * @return a list of MessageDTO objects containing message details
     */
    List<MessageDTO> doGetAllMessages() throws BusinessException;

    /**
     * Retrieves all messages exchanged between a specific sender and receiver.
     *
     * @param senderId the unique identifier of the sender
     * @param receiverId the unique identifier of the receiver
     * @return a list of MessageDTO objects containing the messages between the specified users
     */
    List<MessageDTO> doGetMessagesBetweenUsers(Long senderId, Long receiverId) throws BusinessException;

    /**
     * Creates a new message between users.
     * @param messageDTO the MessageDTO containing the details of the message to be created
     */
    void doCreateMessage(MessageDTO messageDTO) throws BusinessException;

    /**
     * Updates an existing message in the system.
     *
     * @param id the unique identifier of the message to be updated
     * @param messageDTO the MessageDTO containing the updated message details
     */
    void doUpdateMessage(Long id, MessageDTO messageDTO) throws BusinessException;

    /**
     * Deletes a message from the system based on its unique ID.
     * @param id the unique identifier of the message to be deleted
     */
    void deleteMessageById(Long id) throws BusinessException;
}
