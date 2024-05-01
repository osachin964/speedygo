package com.stackroute.authenticationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    @NotEmpty
    private String emailId;
    @NotEmpty
    private String password;
}