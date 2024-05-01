package com.stackroute.chatservice.controller;


import com.stackroute.chatservice.entity.Chat;
import com.stackroute.chatservice.entity.ChatReply;
import com.stackroute.chatservice.exception.IdAlreadyExistException;
import com.stackroute.chatservice.exception.IdNotFoundException;
import com.stackroute.chatservice.service.ChatServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stackroute.chatservice.repository.ChatRepository;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/chatService")
public class ChatController {

    Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private ChatServiceImpl chatServiceImpl;

    @Autowired
    private ChatRepository chatRepository;

    //final SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @PostMapping("/contactUs")
    public Map startChat(@RequestBody Chat chat) throws IdAlreadyExistException {
//
        Map<String, Object> messageObject = new HashMap<String, Object>();
        try {
            chatRepository.save(chat);

            messageObject.put("SenderId", chat.getSenderId());
            messageObject.put("Notification", "chat with chatId "+chat.getChatId() + " has started");
            messageObject.put("Message", chat.getMessage());
            messageObject.put("ReceiverId", chat.getReceiverId());
            //messageObject.put("Date",formatter.format(timestamp));
            messageObject.put("Time", timestamp.toLocalDateTime());
        } catch (Exception e) {
           throw new IdAlreadyExistException("Given chatId already exists,a new chat Id should be assigned");
        }
        return messageObject;
    }

    @PostMapping("/query")
    public Map replyChat(@RequestBody ChatReply chatReply) throws IdNotFoundException {

        Map<String, Object> messageObject = new HashMap<String, Object>();
        try {


            messageObject.put("SenderId", chatReply.getChat().getReceiverId());
            messageObject.put("Chat", chatReply.getChat().getMessage());
            messageObject.put("Reply",chatReply.getReply());
            messageObject.put("ReceiverId", chatReply.getChat().getSenderId());
            //messageObject.put("Date",formatter.format(timestamp));
            messageObject.put("Time", timestamp.toLocalDateTime());

            chatServiceImpl.updateMessage(chatReply.getChat(),chatReply);
            chatRepository.save(chatReply.getChat());
            System.out.println(chatReply.getChat());
        } catch (Exception e) {
                throw new IdNotFoundException("Invalid Chat Id");
        }
        return messageObject;
    }
    @GetMapping("/getchat/{chatId}")
    public ResponseEntity<Chat> getChatByChatId(@PathVariable("chatId") String chatId) throws IdNotFoundException {
        return new ResponseEntity<Chat>(this.chatServiceImpl.getChatById(chatId), HttpStatus.OK);
    }

    @DeleteMapping("/deletechat/{chatId}")
    public ResponseEntity<String> deleteChatByChatId(@PathVariable("chatId") String chatId) throws IdNotFoundException {
        return new ResponseEntity<String>(this.chatServiceImpl.deleteChatById(chatId), HttpStatus.OK);
    }
    @GetMapping("/displayAllChat")
    public ResponseEntity<List<Chat>> getAllChat() {
        return new ResponseEntity<List<Chat>>(this.chatServiceImpl.getAllMessage(), HttpStatus.OK);
    }


}

