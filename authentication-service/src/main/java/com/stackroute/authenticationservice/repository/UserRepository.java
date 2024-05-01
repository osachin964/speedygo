package com.stackroute.authenticationservice.repository;

import com.stackroute.authenticationservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);

}
