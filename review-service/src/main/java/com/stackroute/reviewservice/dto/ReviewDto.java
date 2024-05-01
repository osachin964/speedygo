package com.stackroute.reviewservice.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {


    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String customerEmail;
    private String customerName;
    private String transporterId;
    private String bookingId;
    @Min(value = 1, message = "1 minimum rating")
    @Max(value = 10, message = "10 maximum rating")
    private double rating;
    private String remark;
}
