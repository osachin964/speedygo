package com.stackroute.transporterservice.entity;

import com.stackroute.transporterservice.dto.DriverDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "transporter")
public class Transporter {

    @Id
    private String transporterEmail;

    private String userName;

    private String companyName;

    private Boolean packagingService;

    private String officeAddress;

    private String city;

    private List<String> serviceType;    //Type of services Home Shifting", "Office Shifting", "Car Shifting

    private String gstNo;

    private double experience;

    private int pricePerKm;

    private List<DriverDto> drivers;

    private double rating;
}
