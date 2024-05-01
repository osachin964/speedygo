package com.stackroute.registrationservice.repository;

import com.stackroute.registrationservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User,String> {

    Optional<User> findByEmailId(String emailId);
}
