package com.stackroute.reviewservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "review")
public class Review {

    @Field
    @Id
    private String customerEmail;
    @Field(name ="transporterId")
    private String transporterId;
    @Field(name = "customer_name")
    private String customerName;
    @Field(name ="company_name")
    private String companyName;
    @Field
    private String bookingId;
    @Field(name = "rating")
    private double rating;
    @Field(name = "remark")
    private String remark;


}
