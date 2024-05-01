package com.stackroute.emailservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WelcomeEmail {
    private String emailId;
    private String userName;
}
