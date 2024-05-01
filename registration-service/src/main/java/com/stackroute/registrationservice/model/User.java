package com.stackroute.registrationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "UsersInfo")
public class User {

    @Id
    private String emailId;

    @Field("user_name")
    private String userName;

    @Field("password")
    private String password;

    @Field("confirm_password")
    private String confirmPassword;

    @Field("mobile_no")
    private String mobileNo;

    @Field("user_role")
    private UserRole userRole;


}
