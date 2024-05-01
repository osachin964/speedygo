package com.stackroute.registrationservice.repository;

import com.stackroute.registrationservice.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryTest {

    @Mock
    UserRepo userRepository;

    @Autowired
    Optional<User> user ;


    @Test
    void findByEmailId() {

        String requestId = "rajatsharma@gmail.com";
        user = userRepository.findByEmailId(requestId);
        Mockito.when(userRepository.findByEmailId(requestId)).thenReturn(user);

 }

}
