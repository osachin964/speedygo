package com.stackroute.chatservice.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@EntityScan
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat")
public class Chat {

    @Id
    private String chatId;

    private String senderId;

    private String receiverId;

    private List message;





}
