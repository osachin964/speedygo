package com.stackroute.authenticationservice.rabbitmq;

import lombok.*;
import com.stackroute.authenticationservice.entity.Role;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {


    private String emailId;

    private String userName;
    private String password;
    private String confirmPassword;

    private String mobileNo;
    private Role userRole;
}
