package com.stackroute.transporterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransporterDto {


    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String transporterEmail;
    private String userName;
    private String companyName;
    private Boolean packagingService;
    private String officeAddress;
    private String city;
    private List<String> serviceType;
    private String gstNo;
    private double experience;
    private int pricePerKm;
    private List<DriverDto> drivers;




}
