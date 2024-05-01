package com.stackroute.authenticationservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticationService")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public String userVariable="";
    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String getCustomer()
    {
        return "User "+userVariable+" successfully logged in.\nDear Customer,Welcome to SpeedyGo!";

    }

    @GetMapping("/transporter")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public String getTransporter()
    {
        return "User "+userVariable+" successfully logged in.\nDear Transporter,Welcome to SpeedyGo!";

    }
}