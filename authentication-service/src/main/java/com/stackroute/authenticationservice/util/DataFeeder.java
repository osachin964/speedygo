package com.stackroute.authenticationservice.util;

import com.stackroute.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataFeeder implements CommandLineRunner {
    @Autowired
    UserRepository userRepo;

    @Override
    public void run(String... args) throws Exception {
        userRepo.deleteAll();
    }
}