package com.stackroute.chatservice.repository;

import com.stackroute.chatservice.entity.Chat;
import com.stackroute.chatservice.entity.ChatReply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatReplyRepository extends MongoRepository<ChatReply, Chat> {

}