package com.stackroute.bookingservice.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(collection = "booking")
public class Booking {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
//    @MongoId(value = FieldType.OBJECT_ID)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @MongoId
    private String bookingId;
    @Field("customer_name")
    String customerName;
    @Field("customer_id")
    String customerId;
    @Field("customer_phone")
    String customerMobileNo;
    @Field("drop_location")
    String dropLocation;
    @Field("pickup_location")
    String pickupLocation;
    @Field("drop_address")
    String dropAddress;
    @Field("pickup_address")
    String pickupAddress;
    @Field("transporter_name")
    String transporterName;
    @Field("transporter_id")
    String transporterId;
    @Field("service")
    String serviceType;
    @Field("price")
    Double totalPrice;
    @Field("status")
    String status;
    @Field("driver_name")
    String driverName;
    @Field("driver_contact")
    String driverContact;


}
