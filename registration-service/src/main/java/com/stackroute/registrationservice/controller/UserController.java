package com.stackroute.registrationservice.controller;

import com.stackroute.registrationservice.dto.*;
import com.stackroute.registrationservice.model.UserRole;
import com.stackroute.registrationservice.service.UserService;
import com.stackroute.registrationservice.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registrationService")
@CrossOrigin("*")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private RabbitTemplate template;

    @Autowired(required=true)
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto){
        logger.info("Save User controller");
        logger.info("User Details: {}",userDto.toString());
        UserDto saveUserDto = userService.saveUser(userDto);
        EmailDto emailQueue = new EmailDto();
        emailQueue.setEmailId(userDto.getEmailId());
        emailQueue.setUserName(userDto.getUserName());
        template.convertAndSend(AppConstants.EXCHANGE, AppConstants.USER_QUEUE_ROUTING_KEY,userDto);
        template.convertAndSend(AppConstants.EXCHANGE, AppConstants.EMAIL_QUEUE_ROUTING_KEY,emailQueue);
        if(saveUserDto.getUserRole().equals(UserRole.CUSTOMER)){
         CustomerPublishDto customerPublishDto = new CustomerPublishDto();
         customerPublishDto.setEmailId(userDto.getEmailId());
         customerPublishDto.setUserName(userDto.getUserName());
         customerPublishDto.setMobileNo(userDto.getMobileNo());
         template.convertAndSend(AppConstants.EXCHANGE, AppConstants.CUSTOMER_QUEUE_ROUTING_KEY,customerPublishDto);
         }
        if(saveUserDto.getUserRole().equals(UserRole.TRANSPORTER)){
            TransporterPublishDto transporterPublishDto = new TransporterPublishDto();
            transporterPublishDto.setEmailId(userDto.getEmailId());
            transporterPublishDto.setUserName(userDto.getUserName());
            template.convertAndSend(AppConstants.EXCHANGE, AppConstants.TRANSPORTER_QUEUE_ROUTING_KEY,transporterPublishDto);
         }
        return new ResponseEntity<>(saveUserDto, HttpStatus.OK);
    }


    @GetMapping( "/getAllUsers")
    public ResponseEntity<List<UserDto>> findAll() {
        logger.info("Get all User");
        return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/getUser/{emailId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("emailId") String emailId) {
        logger.info("Get User by Email Id");
        logger.info("Email Id :" +emailId);
       return new ResponseEntity<>(this.userService.findByEmailId(emailId), HttpStatus.OK);
    }


    @PostMapping("/send-otp/{emailId}")
    public String sendOTP(@PathVariable("emailId") String emailId,HttpSession session) {
        int otp   =(int) (Math.random()*9000)+1000;
       boolean flag = true;
        if(flag)
        {

            session.setAttribute("myotp", otp);
            session.setAttribute("email", emailId);
            EmailVerificationDto emailDto = new EmailVerificationDto();
            emailDto.setEmailId(emailId);
            emailDto.setOtp(otp);
            template.convertAndSend(AppConstants.EXCHANGE, AppConstants.OTP_QUEUE_ROUTING_KEY ,emailDto);
            return "verify_otp " + otp;

        }else
        {

            session.setAttribute("message", "Check your email id !!");

            return "forgot_email_form";
        }


    }



    //verify otp
    @PostMapping("/verify-otp/{otp}")
    public String verifyOtp(@PathVariable("otp") int otp,HttpSession session)
    {
        int myOtp=(int)session.getAttribute("myotp");
        String email=(String)session.getAttribute("email");
        if(myOtp==otp)
        {
            //password change form
            UserDto user = this.userService.findByEmailId(email);

            if(user==null)
            {
                //send error message
                return "forgot_email_form";
            }else
            {
                 return "You can proceed to change your password";

            }

        }else
        {
            return "You have entered wrong otp ";
        }
    }


    //change password
//    @PutMapping("/updatePassword")
//    public ResponseEntity<UserDto> updatePassword(@RequestParam("emailId") String emailId,@RequestParam("newPassword") String newPassword){
//        UserDto updatePasswordDto = userService.updatePassword(emailId,newPassword);
//        NewPasswordDto newPasswordDto = new NewPasswordDto();
//        newPasswordDto.setEmailId(emailId);
//        newPasswordDto.setNewPassword(newPassword);
//        template.convertAndSend(AppConstants.EXCHANGE, AppConstants.PASSWORD_QUEUE_ROUTING_KEY,newPasswordDto);
//        return ResponseEntity.ok(updatePasswordDto);
//    }

    @PutMapping("/updatePassword")
    public ResponseEntity<UserDto> updatePassword(@RequestParam("otp") int otp,@RequestParam("emailId") String emailId,@RequestParam("newPassword") String newPassword, HttpSession session) {
        int myOtp = (int) session.getAttribute("myotp");
//        String email = (String) session.getAttribute("email");
        if (myOtp == otp) {
            UserDto updatePasswordDto = userService.updatePassword(emailId, newPassword);
            NewPasswordDto newPasswordDto = new NewPasswordDto();
            newPasswordDto.setEmailId(emailId);
            newPasswordDto.setNewPassword(newPassword);
            template.convertAndSend(AppConstants.EXCHANGE, AppConstants.PASSWORD_QUEUE_ROUTING_KEY, newPasswordDto);
            return ResponseEntity.ok(updatePasswordDto);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        logger.info("Inside deleteCustomer controller");
        logger.info("Customer email to delete : "+email);
        String response = userService.deleteUser(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
