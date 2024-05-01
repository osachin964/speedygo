package com.stackroute.emailservice.service.impl;

import com.stackroute.emailservice.AppConstants;
import com.stackroute.emailservice.exception.EmailPropertiesEmptyException;
import com.stackroute.emailservice.model.*;
import com.stackroute.emailservice.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;



@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration config;

    String[] cC = {"Vidhii9589@gmail.com", "twinklejangid98@gmail.com", "dhanashriadgurwar@gmail.com", "shivangisng31@gmail.com","osachin964@gmail.com"};

    /**
     * @param emailProperties - email object
     * @return - true/false on email success/failure
     */
    @Override
    public boolean sendEmail(Email emailProperties) {
            boolean emailSent = false;
            validateEmailProperties(emailProperties);
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(AppConstants.SENDER_EMAIL);
            email.setTo(emailProperties.getTo());
            email.setSubject(emailProperties.getSubject());
            email.setText(emailProperties.getMessage());
            emailSender.send(email);
            emailSent = true;

        return emailSent;
    }

    @Override
    public boolean sendEmailCc(Email emailProperties) {
        boolean emailSent = false;
        try {
            validateEmailProperties(emailProperties);
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(AppConstants.SENDER_EMAIL);
            email.setTo(emailProperties.getTo());
            email.setCc("Vidhii9589@gmail.com","twinklejangid98@gmail.com","dhanashriadgurwar@gmail.com","shivangisng31@gmail.com");
            email.setSubject(emailProperties.getSubject());
            email.setText(emailProperties.getMessage());

            emailSender.send(email);
            emailSent = true;
        } catch (EmailPropertiesEmptyException | MailException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailSent;
    }

    public void validateEmailProperties(Email emailProperties) {
        if (!StringUtils.isNotBlank(emailProperties.getTo())) {
            throw new EmailPropertiesEmptyException("receiver email is Empty or null");
        } else if (!StringUtils.isNotBlank(emailProperties.getSubject())) {
            throw new EmailPropertiesEmptyException("subject of the mail is empty");
        } else if (!StringUtils.isNotBlank(emailProperties.getMessage())) {
            throw new EmailPropertiesEmptyException("body of the mail is empty");
        }
    }

    @Override
    public boolean sendEmailTemplate(WelcomeEmail email) {
        boolean response;
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("email-template.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("username", email.getUserName());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(email.getEmailId());

            helper.setText(html, true);
            helper.setSubject("Welcome to SpeedyGo");
            helper.setFrom(AppConstants.SENDER_EMAIL);
            emailSender.send(message);
            response = true;
        }catch (MessagingException | IOException | TemplateException e){
            response = false;
        }
        return response;
    }

    /**
     * @param email -order confirmation object
     * @return boolean value true/false
     */
    @Override
    public boolean sendBookingConfirmationEmail(OrderConfirmationEmail email) {
        boolean response;
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("Order-confirmation-template.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("username", email.getCustomerName());
            model.put("bookingId",email.getBookingId());
            model.put("serviceType",email.getServiceType());
            model.put("pickupLocation",email.getPickupLocation());
            model.put("dropLocation",email.getDropLocation());
            model.put("totalPrice",email.getTotalPrice());
            model.put("driverName",email.getDriverName());
            model.put("driverPhone",email.getDriverPhone());

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(email.getCustomerId());

            helper.setText(html, true);
            helper.setSubject("Booking Confirmation!");
            helper.setFrom(AppConstants.SENDER_EMAIL);
            emailSender.send(message);
            response = true;
        }catch (MessagingException | IOException | TemplateException e){
            response = false;
        }
        return response;
    }

    /**
     * @param otpDto - details of otp email in a object
     * @return - boolean value true/false
     */
    @Override
    public boolean sendOtpEmail(OtpDto otpDto) {
        boolean response;
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("OTP-email-template.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("otp", String.valueOf(otpDto.getOtp()));
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(otpDto.getEmailId());

            helper.setText(html, true);
            helper.setSubject("One time Password!");
            helper.setFrom(AppConstants.SENDER_EMAIL);
            emailSender.send(message);
            response = true;
        }catch (MessagingException | IOException | TemplateException e){
            response = false;
        }
        return response;
    }

    @Override
    public boolean sendStatusEmail(StatusEmailDto statusEmailDto) {
        boolean response;
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("status-change.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("status", statusEmailDto.getStatus());
            model.put("bookingId", statusEmailDto.getBookingId());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(statusEmailDto.getCustomerId());
            helper.setText(html, true);
            helper.setSubject("Booking Status Changed");
            helper.setFrom(AppConstants.SENDER_EMAIL);
            emailSender.send(message);
            response = true;
        }catch (MessagingException | IOException | TemplateException e){
            response = false;
        }
        return response;
    }
}

