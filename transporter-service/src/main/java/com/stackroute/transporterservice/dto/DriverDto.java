package com.stackroute.transporterservice.dto;

import lombok.*;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DriverDto {


    private String driverId;
    private String driverName;
    @Pattern(regexp = "^\\d{10}$", message = "Enter 10 digit mobile number")
    private String driverMobileNo;
    private String transporterEmail;
}
