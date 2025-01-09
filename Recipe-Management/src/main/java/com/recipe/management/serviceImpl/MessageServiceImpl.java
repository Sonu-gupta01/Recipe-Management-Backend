package com.recipe.management.serviceImpl;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.dao.MessageDao;
import com.recipe.management.dto.MessageDTO;
import com.recipe.management.entity.Messages;
import com.recipe.management.entity.Users;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.exception.DataServiceException;
import com.recipe.management.service.MessageService;
import com.recipe.management.util.EntityToDTOConverter;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private EntityToDTOConverter converter;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public MessageDTO doGetMessageById(Long id) throws BusinessException {
        try {
            return Optional.ofNullable(messageDao.findMessageById(id))
                    .map(converter::convertToMessageDTO)
                    .orElseThrow(() -> new BusinessException(ErrorMessages.MESSAGE_NOT_FOUND + id));
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MESSAGE_FETCH_ERROR + id, e);
        }
    }

    @Override
    public List<MessageDTO> doGetAllMessages() throws BusinessException {
        try {
            return messageDao.findAllMessages().stream()
                    .map(converter::convertToMessageDTO)
                    .collect(Collectors.toList());
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MESSAGE_FETCH_ALL_ERROR, e);
        }
    }

    @Override
    public List<MessageDTO> doGetMessagesBetweenUsers(Long senderId, Long receiverId) throws BusinessException {
        try {
            return messageDao.findMessagesBySenderAndReceiver(senderId, receiverId).stream()
                    .map(converter::convertToMessageDTO)
                    .collect(Collectors.toList());
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MESSAGE_FETCH_BETWEEN_USERS_ERROR, e);
        }
    }

    @Override
    public void doCreateMessage(MessageDTO messageDTO) throws BusinessException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Users sender = session.get(Users.class, messageDTO.getSenderId());
            Users receiver = session.get(Users.class, messageDTO.getReceiverId());
            if (sender == null || receiver == null) {
                throw new BusinessException(ErrorMessages.MESSAGE_USER_NOT_FOUND);
            }
            Messages message = converter.convertToMessageEntity(messageDTO);
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setSentOn(LocalDateTime.now());
            messageDao.saveMessage(message);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MESSAGE_SAVE_ERROR, e);
        }
    }

    @Override
    public void doUpdateMessage(Long id, MessageDTO messageDTO) throws BusinessException {
        try {
            Messages existingMessage = messageDao.findMessageById(id);
            if (existingMessage == null) {
                throw new BusinessException(ErrorMessages.MESSAGE_NOT_FOUND + id);
            }
            existingMessage.setMessageText(messageDTO.getMessageText());
            messageDao.saveMessage(existingMessage);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MESSAGE_UPDATE_ERROR + id, e);
        }
    }

    @Override
    public void deleteMessageById(Long id) throws BusinessException {
        try {
            messageDao.deleteMessageById(id);
        } catch (DataServiceException e) {
            throw new BusinessException(ErrorMessages.MESSAGE_DELETE_ERROR + id, e);
        }
    }
}
