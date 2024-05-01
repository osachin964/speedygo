package com.stackroute.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    String bookingId;
    String customerId;
    String transporterId;
    String customerName;

}
