package com.recipe.management.controller;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.RestConstants;
import com.recipe.management.dto.MessageDTO;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = RestConstants.BASE_URL)
@RequestMapping(RestConstants.MESSAGES_BASE)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping(RestConstants.MESSAGE_BY_ID)
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(messageService.doGetMessageById(id));
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() throws BusinessException {
        return ResponseEntity.ok(messageService.doGetAllMessages());
    }

    @GetMapping(RestConstants.MESSAGES_BETWEEN_USERS)
    public ResponseEntity<List<MessageDTO>> getMessagesBetweenUsers(@PathVariable Long senderId, @PathVariable Long receiverId) throws BusinessException {
        return ResponseEntity.ok(messageService.doGetMessagesBetweenUsers(senderId, receiverId));
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody MessageDTO messageDTO) throws BusinessException {
        messageService.doCreateMessage(messageDTO);
        return ResponseEntity.ok(ErrorMessages.MESSAGE_CREATED);
    }

    @PutMapping(RestConstants.MESSAGE_BY_ID)
    public ResponseEntity<String> updateMessage(@PathVariable Long id, @RequestBody MessageDTO messageDTO) throws BusinessException {
        messageService.doUpdateMessage(id, messageDTO);
        return ResponseEntity.ok(ErrorMessages.MESSAGE_UPDATED);
    }

    @DeleteMapping(RestConstants.MESSAGE_BY_ID)
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) throws BusinessException {
        messageService.deleteMessageById(id);
        return ResponseEntity.ok(ErrorMessages.MESSAGE_DELETED);
    }
}

