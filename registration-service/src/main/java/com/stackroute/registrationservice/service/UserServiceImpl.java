package com.stackroute.registrationservice.service;

import com.stackroute.registrationservice.dto.UserDto;
import com.stackroute.registrationservice.exception.IdNotFoundException;
import com.stackroute.registrationservice.exception.UserAlreadyExistException;
import com.stackroute.registrationservice.model.User;
import com.stackroute.registrationservice.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (userRepository.existsById(userDto.getEmailId())) {
            throw new UserAlreadyExistException();
        }
        User user = userRepository.save(userDtoToEntity(userDto));
        return userEntityToDto(user);
    }


    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> user = userRepository.findAll();
        for (User u : user) {
            userDtos.add(userEntityToDto(u));
        }
        return userDtos;
    }

    @Override
    public String deleteUser(String emailId) {
        String response;
        if (userRepository.existsById(emailId)) {
            userRepository.deleteById(emailId);
            response = "User Account Deleted";
        } else {
            response = "User not found";
        }
        return response;
    }


    @Override
    public UserDto findByEmailId(String emailId) {
        Optional<User> user = userRepository.findByEmailId(emailId);
       // System.out.println(user.toString());
        if (user.isEmpty()) {
            throw new IdNotFoundException();
        }
        return userEntityToDto(user);

    }

    @Override
    public UserDto updatePassword(String emailId, String newPassword) throws IdNotFoundException{
        User user = userRepository.findByEmailId(emailId).orElseThrow(UserAlreadyExistException::new);
        user.setPassword(newPassword);
        user.setConfirmPassword(newPassword);
        User updated = userRepository.save(user);
        return userEntityToDto(updated);
}

    private UserDto userEntityToDto(Optional<User> user) {
        return new ModelMapper().map(user, UserDto.class);
    }


    User userDtoToEntity(UserDto userDto) {
        return new ModelMapper().map(userDto, User.class);
    }

    private UserDto userEntityToDto(User user) {
        return new ModelMapper().map(user, UserDto.class);
    }


}

