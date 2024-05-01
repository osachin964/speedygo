package com.stackroute.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingProducer {
    private String emailId;
    private String userName;
    private String mobileNo;
}
