package com.stackroute.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerConsumerDto {
    private String emailId;
    private String userName;
    private String mobileNo;
}
