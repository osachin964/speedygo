package com.stackroute.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewConsumerDto {

    String bookingId;
    @Id
    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String customerId;

    String transporterId;
    String customerName;
}
