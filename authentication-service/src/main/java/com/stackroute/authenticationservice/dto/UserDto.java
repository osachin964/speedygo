package com.stackroute.authenticationservice.dto;

import com.stackroute.authenticationservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String emailId;
    private String password;
    private Role role;
}
