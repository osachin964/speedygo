package com.stackroute.customerservice.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {


    @Email(message = "Enter valid email address")
    private String emailId;
    @Size(min = 6, max = 10, message = "User Name should be min 6 character maximum 10 characters")
    private String userName;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^\\d{10}$", message = "Enter 10 digit mobile number")
    private String mobileNo;
    private String city;
    private String state;
    private String pincode;

}
