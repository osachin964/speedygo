package com.stackroute.transporterservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "drivers")
public class Driver {


    @Id
    private String driverId = UUID.randomUUID().toString();

    private String driverName;

    private String driverMobileNo;

    private String transporterEmail;


}
