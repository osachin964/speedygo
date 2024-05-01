package com.stackroute.registrationservice.utils;

import com.stackroute.registrationservice.controller.UserController;
import com.stackroute.registrationservice.dto.UserDto;
import com.stackroute.registrationservice.model.UserRole;
import com.stackroute.registrationservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataFeeder implements CommandLineRunner {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserController userController;
    @Override
    public void run(String... args) throws Exception {
        userRepo.deleteAll();
        //UserDto userDtoCust =new UserDto("gttrades","ExpressWay","vidhi123","vidhi123","8779887965", UserRole.TRANSPORTER);
        UserDto userDtoCust =new UserDto("expressway@gmail.com","ExpressWay","vidhi123","vidhi123","8779887965", UserRole.TRANSPORTER);
        UserDto userDtoTrans =new UserDto("shivangisng31@gmail.com","Shivangi Singh","vidhi123","vidhi123","8779887665", UserRole.CUSTOMER);
        userController.save(userDtoCust);
        userController.save(userDtoTrans);
//        userRepo.save(User.builder().emailId("quickliners@gmail.com").password("vidhi123").userRole(UserRole.TRANSPORTER).confirmPassword("vidhi123").build());
//        userRepo.save(User.builder().emailId("localmovers@gmail.com").password("vidhi123").userRole(UserRole.TRANSPORTER).confirmPassword("vidhi123").build());
//        userRepo.save(User.builder().emailId("shiftkaro@gmail.com").password("vidhi123").userRole(UserRole.TRANSPORTER).confirmPassword("vidhi123").build());
//        userRepo.save(User.builder().emailId("ramtransporter@gmail.com").password("vidhi123").userRole(UserRole.TRANSPORTER).confirmPassword("vidhi123").build());
//        userRepo.save(User.builder().emailId("bluedart@gmail.com").password("vidhi123").userRole(UserRole.TRANSPORTER).confirmPassword("vidhi123").build());
//        userRepo.save(User.builder().emailId("jmd@gmail.com").password("vidhi123").userRole(UserRole.TRANSPORTER).confirmPassword("vidhi123").build());
//        userRepo.save(User.builder().emailId("quickmovers@gmail.com").password("vidhi123").userRole(UserRole.TRANSPORTER).confirmPassword("vidhi123").build());
//        userRepo.save(User.builder().emailId("gltransporters@gmail.com").password("vidhi123").userRole(UserRole.TRANSPORTER).confirmPassword("vidhi123").build());
//















    }
}
