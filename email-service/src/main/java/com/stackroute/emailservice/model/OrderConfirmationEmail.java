package com.stackroute.emailservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderConfirmationEmail {

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
