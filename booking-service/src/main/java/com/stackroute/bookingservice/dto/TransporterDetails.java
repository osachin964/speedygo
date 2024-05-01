package com.stackroute.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterDetails {

    String transporterEmail;
    String companyName;
    String experience;
    String officeAddress;
    //String mobileNo;
    Double ratings;
    Double calculatedPrice;

    List<String> serviceType;
}
