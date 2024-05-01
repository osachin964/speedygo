package com.stackroute.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EntityScan
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat")
public class ChatReply {

    private Chat chat;

    private String reply;

}
