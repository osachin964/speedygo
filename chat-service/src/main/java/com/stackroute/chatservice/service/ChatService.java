package com.stackroute.chatservice.service;

import com.stackroute.chatservice.entity.Chat;
import com.stackroute.chatservice.entity.ChatReply;
import com.stackroute.chatservice.exception.IdNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {

   Chat getChatById(String chatId) throws IdNotFoundException;

    String deleteChatById(String chatId) throws IdNotFoundException;

    List<Chat> getAllMessage();

   String updateMessage(Chat chat,ChatReply chatReply);

}
