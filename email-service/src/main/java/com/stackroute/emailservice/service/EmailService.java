package com.stackroute.emailservice.service;

import com.stackroute.emailservice.model.*;

public interface EmailService {

    boolean sendEmail(Email email);

    boolean sendEmailCc(Email email);

    boolean sendEmailTemplate(WelcomeEmail email);

    boolean sendBookingConfirmationEmail(OrderConfirmationEmail email);

    boolean sendOtpEmail(OtpDto otpDto);

    boolean sendStatusEmail(StatusEmailDto statusEmailDto);
}
