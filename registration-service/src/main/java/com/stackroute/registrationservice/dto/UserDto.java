package com.stackroute.registrationservice.dto;

import com.stackroute.registrationservice.model.UserRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @Email(message = "Enter valid email address")
    private String emailId;
    @Size(min = 4, max = 10, message = "User Name should be min 4 character maximum 10 characters")
    private String userName;
    private String password;
    private String confirmPassword;
    @Pattern(regexp = "^\\d{10}$", message = "Enter 10 digit mobile number")
    private String mobileNo;
    private UserRole userRole;
}
