package com.stackroute.registrationservice.service;

import com.stackroute.registrationservice.dto.UserDto;
import com.stackroute.registrationservice.exception.UserAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    UserDto saveUser(UserDto userDto) throws UserAlreadyExistException;
    UserDto findByEmailId(String emailId);
    UserDto updatePassword(String emailId,String newPassword);
    List<UserDto> findAll();
    String deleteUser(String emailId);
}
