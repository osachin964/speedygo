package com.stackroute.transporterservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransporterConsumerDto {

//    @Id
//    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String emailId;

    private String userName;
}
