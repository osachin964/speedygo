package com.stackroute.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    String bookingId;
    String customerId;
    String customerName;
    String dropLocation;
    String pickupLocation;
    String serviceType;
    Double totalPrice;
    String driverName;
    String driverPhone;

}
