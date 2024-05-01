package com.stackroute.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDto {
    String customerName;
    String customerId;
    String customerMobileNo;
    String dropAddress;
    String dropLocation;
    String pickupAddress;
    String pickupLocation;
    String transporterId;
    String serviceType;

}
