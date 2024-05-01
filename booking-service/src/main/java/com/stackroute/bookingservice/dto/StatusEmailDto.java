package com.stackroute.bookingservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusEmailDto {
    String customerId;
    String bookingId;
    String status;

}
