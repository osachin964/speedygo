package com.stackroute.bookingservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSearchRequest {
    private String type;
    private String id;
}
