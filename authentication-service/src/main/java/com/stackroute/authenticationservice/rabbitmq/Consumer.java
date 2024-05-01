package com.stackroute.authenticationservice.rabbitmq;

import com.stackroute.authenticationservice.dto.UserDto;
import com.stackroute.authenticationservice.entity.Role;
import com.stackroute.authenticationservice.entity.Users;
import com.stackroute.authenticationservice.repository.UserRepository;
import com.stackroute.authenticationservice.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private UserService UserService;

    @Autowired
    private UserRepository userRepository;

    @RabbitListener(queues = "Authentication Queue")
    public void consumeMessageFromQueue(RegisterUserDto userDTO) {
        if (userDTO != null) {
            System.out.println("Authentication details received: " + userDTO.toString());
            UserDto user = new UserDto();
            user.setEmailId(userDTO.getEmailId());
            user.setPassword(userDTO.getPassword());
            user.setRole(userDTO.getUserRole());
            System.out.println(user);
            UserService.saveUser(user);
        }
    }

    @RabbitListener(queues = "Password Queue")
    public void consumeMessageFromQueue(UpdatedPasswordDto userDTO) {
        if (userDTO != null) {
            System.out.println("Updated password details received: " + userDTO.toString());
            UserDto user = new UserDto();
            Users user1 = userRepository.findByUsername(userDTO.getEmailId());
            user.setEmailId(userDTO.getEmailId());
            user.setPassword(userDTO.getNewPassword());
            user.setRole(Role.valueOf(user1.getRole()));
            System.out.println(user);
            UserService.saveUser(user);
        }
    }


}
