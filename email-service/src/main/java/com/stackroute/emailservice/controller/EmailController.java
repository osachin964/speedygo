package com.stackroute.emailservice.controller;

import com.stackroute.emailservice.model.WelcomeEmail;
import com.stackroute.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/emailService")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendmail")
    public ResponseEntity<String> sendEmail(@RequestBody WelcomeEmail emailParameters) {
        String message;
        boolean status = emailService.sendEmailTemplate(emailParameters);
        if (status) {
            message = "Email has been sent successfully";
        } else {
            message = "Email sending failed";
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
