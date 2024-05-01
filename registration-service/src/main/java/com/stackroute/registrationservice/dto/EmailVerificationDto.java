package com.stackroute.registrationservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailVerificationDto {

    private String emailId;
    private int otp;
}
