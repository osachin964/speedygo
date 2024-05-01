package com.stackroute.emailservice.config;

import com.stackroute.emailservice.AppConstants;
import com.stackroute.emailservice.exception.EmailPropertiesEmptyException;
import com.stackroute.emailservice.model.OrderConfirmationEmail;
import com.stackroute.emailservice.model.OtpDto;
import com.stackroute.emailservice.model.StatusEmailDto;
import com.stackroute.emailservice.model.WelcomeEmail;
import com.stackroute.emailservice.service.EmailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
@Slf4j
public class EmailListner {

    public static final String CONSUMED_MESSAGE_SUCCESS = "message consumed and email sent successfully";
    public static final String CONSUMED_MESSAGE_FAIL = "Email id or username is empty";

    @Autowired
    EmailService emailService;

    @RabbitListener(queues = AppConstants.EMAIL_QUEUE)
    public void welcomeEmailListen(WelcomeEmail email) throws MessagingException, TemplateException, IOException {
        if (null != email.getEmailId() && null != email.getUserName()) {
            emailService.sendEmailTemplate(email);
            log.info(CONSUMED_MESSAGE_SUCCESS);
        }else {
            throw new EmailPropertiesEmptyException(CONSUMED_MESSAGE_FAIL);
        }
    }

    @RabbitListener(queues = AppConstants.BOOKING_EMAIL_QUEUE)
    public void bookingEmailListen(OrderConfirmationEmail email) throws MessagingException, TemplateException, IOException {
        if (null != email.getCustomerId()) {
            emailService.sendBookingConfirmationEmail(email);
            log.info(CONSUMED_MESSAGE_SUCCESS);
        }else {
            throw new EmailPropertiesEmptyException(CONSUMED_MESSAGE_FAIL);
        }
    }

    @RabbitListener(queues = AppConstants.OTP_QUEUE)
    public void otpEmailListen(OtpDto email) throws MessagingException, TemplateException, IOException {
        if (null != email.getEmailId()) {
            emailService.sendOtpEmail(email);
            log.info(CONSUMED_MESSAGE_SUCCESS);
        }else {
            throw new EmailPropertiesEmptyException(CONSUMED_MESSAGE_FAIL);
        }
    }

    @RabbitListener(queues = AppConstants.STATUS_QUEUE)
    public void statusEmailListen(StatusEmailDto email) throws MessagingException, TemplateException, IOException {
        if (null != email.getCustomerId()) {
            emailService.sendStatusEmail(email);
            log.info(CONSUMED_MESSAGE_SUCCESS);
        }else {
            throw new EmailPropertiesEmptyException(CONSUMED_MESSAGE_FAIL);
        }
    }
}
