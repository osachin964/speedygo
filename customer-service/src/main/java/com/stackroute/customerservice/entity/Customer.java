package com.stackroute.customerservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
@Builder
public class Customer {

    @Id
    @Field("email_id")
    private String emailId;

    @Field("user_name")
    private String userName;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("mobile_no")
    private String mobileNo;

    @Field("city")
    private String city;

    @Field("state")
    private String state;

    @Field("pin_code")
    private String pincode;

}
