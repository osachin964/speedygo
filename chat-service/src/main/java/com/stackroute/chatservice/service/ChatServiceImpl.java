package com.stackroute.chatservice.service;

import com.stackroute.chatservice.entity.Chat;
import com.stackroute.chatservice.entity.ChatReply;
import com.stackroute.chatservice.exception.IdNotFoundException;
import com.stackroute.chatservice.repository.ChatRepository;
import com.stackroute.chatservice.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    public
    ChatRepository chatRepository;

    @Override
    public Chat getChatById(String chatId) throws IdNotFoundException {
        return chatRepository.findById(String.valueOf(chatId)).orElseThrow(()->new IdNotFoundException(AppConstants.ID_NOT_FOUND_MESSAGE));
    }

    @Override
    public String deleteChatById(String chatId) throws IdNotFoundException {
        Chat msg=chatRepository.findById(String.valueOf(chatId)).orElseThrow(()-> new IdNotFoundException(AppConstants.ID_NOT_FOUND_MESSAGE));
        chatRepository.delete(msg);
        return AppConstants.APT_DELETE_SUCCESS_MESSAGE;
    }


    @Override
    public List<Chat> getAllMessage() {
        List<Chat> chat=chatRepository.findAll();
        return chat;
    }

    @Override
    public String updateMessage(Chat chat,ChatReply chatReply){

        if(chat.getChatId()==chatReply.getChat().getChatId()){
            chat.getMessage().add(chatReply.getReply());
            System.out.println(chat);
        }
        return "Chat saved successfully";
    }
}
