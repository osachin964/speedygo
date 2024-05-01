package com.stackroute.chatservice;

import com.stackroute.chatservice.entity.Chat;
import com.stackroute.chatservice.entity.ChatReply;
import com.stackroute.chatservice.exception.IdNotFoundException;
import com.stackroute.chatservice.repository.ChatRepository;
import com.stackroute.chatservice.service.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ChatServiceTests {

    @Mock
    ChatRepository chatRepository;

    @InjectMocks
    private ChatServiceImpl chatServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        chatServiceImplUnderTest = new ChatServiceImpl();
        chatServiceImplUnderTest.chatRepository = mock(ChatRepository.class);
    }

    @Test
    void testGetChatById() throws IdNotFoundException {
        List<String> message = new ArrayList<>();
        message.add("Hi speedygo");

        // Setup
        final Chat expectedResult = new Chat("SG1", "shivangis31@gmail.com", "go.speedy14@gmail.com",message );

        // Configure ChatRepository.findById(...).
        final Optional<Chat> chat = Optional.of(new Chat("SG1", "shivangis31@gmail.com", "go.speedy14@gmail.com", message));
        when(chatServiceImplUnderTest.chatRepository.findById("SG1")).thenReturn(chat);

        // Run the test
        final Chat result = chatServiceImplUnderTest.getChatById("SG1");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetChatById_ChatRepositoryReturnsAbsent() {
        // Setup
        when(chatServiceImplUnderTest.chatRepository.findById("SG2")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> chatServiceImplUnderTest.getChatById("SG2"))
                .isInstanceOf(IdNotFoundException.class);
    }

    @Test
    void testGetChatById_ThrowsIdNotFoundException() {
        // Setup
        // Configure ChatRepository.findById(...).
        List<String> message = new ArrayList<>();
        message.add("Hi speedygo");
        final Optional<Chat> chat = Optional.of(new Chat("SG1", "shivangis31@gmail.com", "go.speedy14@gmail.com", message));
        when(chatServiceImplUnderTest.chatRepository.findById("SG1")).thenReturn(chat);

        // Run the test
        assertThatThrownBy(() -> chatServiceImplUnderTest.getChatById("SG2"))
                .isInstanceOf(IdNotFoundException.class);
    }

    @Test
    void testDeleteChatById() throws IdNotFoundException {
        // Setup
        // Configure ChatRepository.findById(...).
        List<String> message = new ArrayList<>();
        message.add("Hi speedygo");
        final Optional<Chat> chat = Optional.of(new Chat("SG1", "shivangis31@gmail.com", "go.speedy14@gmail.com", message));
        when(chatServiceImplUnderTest.chatRepository.findById("SG1")).thenReturn(chat);

        // Run the test
        final String result = chatServiceImplUnderTest.deleteChatById("SG1");

        // Verify the results
        assertThat(result).isEqualTo("Chat Deleted Successfully");
        verify(chatServiceImplUnderTest.chatRepository).delete(new Chat("SG1", "shivangis31@gmail.com", "go.speedy14@gmail.com", message));
    }

    @Test
    void testDeleteChatById_ChatRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(chatServiceImplUnderTest.chatRepository.findById("SG3")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> chatServiceImplUnderTest.deleteChatById("SG3"))
                .isInstanceOf(IdNotFoundException.class);
    }

    @Test
    void testDeleteChatById_ThrowsIdNotFoundException() {
        // Setup
        // Configure ChatRepository.findById(...).
        final Optional<Chat> chat = Optional.of(new Chat("SG4", null, null, List.of()));
        when(chatServiceImplUnderTest.chatRepository.findById("SG4")).thenReturn(chat);

        // Run the test
        assertThatThrownBy(() -> chatServiceImplUnderTest.deleteChatById("SG5"))
                .isInstanceOf(IdNotFoundException.class);
    }

    @Test
    void testGetAllMessage() {
        // Setup
        List<String> message1 = new ArrayList<>();
        message1.add("Hi speedygo");
        message1.add("Hi Shiv,how can we help you?");

        List<String> message2 = new ArrayList<>();
        message2.add("Hi speedygo");
        message2.add("Hi raj,how can we help you?");
        final List<Chat> expectedResult = new ArrayList<Chat>();
        expectedResult.add(new Chat("SG1", "shiv@gmail.com", "go.speedygo@gmail.com", message1));
        expectedResult.add(new Chat("SG2", "raj@gmail.com", "go.speedygo@gmail.com", message2));

        // Configure ChatRepository.findAll(...).
        final List<Chat> chats = List.of(new Chat("SG1", "shiv@gmail.com", "go.speedygo@gmail.com", message1),new Chat("SG2", "raj@gmail.com", "go.speedygo@gmail.com", message2));
        when(chatServiceImplUnderTest.chatRepository.findAll()).thenReturn(chats);

        // Run the test
        final List<Chat> result = chatServiceImplUnderTest.getAllMessage();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllMessage_ChatRepositoryReturnsNoItems() {
        // Setup
        when(chatServiceImplUnderTest.chatRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Chat> result = chatServiceImplUnderTest.getAllMessage();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testUpdateMessage() {
        // Setup
        List<String> message2 = new ArrayList<>();
        message2.add("Hi speedygo,I need to know about best transporters");
        final Chat chat = new Chat("SG5", "harsh@gmail.com", "go.speedy14@gmail.com", message2);
        final ChatReply chatReply = new ChatReply(new Chat("SG5", "harsh@gmail.com", "go.speedy14@gmail.com", message2), "Hi Harsh,please mention the city you want service in ");

        // Run the test
        final String result = chatServiceImplUnderTest.updateMessage(chat, chatReply);

        // Verify the results
        assertThat(result).isEqualTo("Chat saved successfully");
    }
}
