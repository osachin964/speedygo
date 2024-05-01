package com.stackroute.registrationservice.service;


import com.stackroute.registrationservice.dto.UserDto;
import com.stackroute.registrationservice.exception.UserAlreadyExistException;
import com.stackroute.registrationservice.model.User;
import com.stackroute.registrationservice.model.UserRole;
import com.stackroute.registrationservice.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;

    @BeforeEach
    public void setup() {
        userDto = UserDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .password("HELLO")
                .confirmPassword("HELLO")
                .mobileNo("8800112233")
                .userRole(UserRole.TRANSPORTER).build();
    }

    @DisplayName("JUnit test for saveUser method")
    @Test
    public void saveUserTest() {
        given(userRepository.existsById(userDto.getEmailId()))
                .willReturn(false);

        User entity = userService.userDtoToEntity(userDto);
        given(userRepository.save(entity)).willReturn(entity);


        UserDto savedUser = userService.saveUser(userDto);

        assertThat(savedUser).isNotNull();
    }

    @DisplayName("JUnit test for saveUser method which throws exception")
    @Test
    public void saveUserTestCaseFail() {
        given(userRepository.existsById(userDto.getEmailId()))
                .willReturn(true);


        assertThrows(UserAlreadyExistException.class, () -> userService.saveUser(userDto));

        verify(userRepository, never()).save(userService.userDtoToEntity(userDto));

    }

    @DisplayName("JUnit test for getAllCustomers method")
    @Test
    public void getAllUserListTest() {

        UserDto customerDto1 = UserDto.builder()
                .emailId("anandK123@gmail.com")
                .userName("anandkk")
                .password("HELLO")
                .confirmPassword("HELLO")
                .mobileNo("8112233800")
                .userRole(UserRole.CUSTOMER).build();

        List<User> list = new ArrayList<>();
        list.add(userService.userDtoToEntity(userDto));
        list.add(userService.userDtoToEntity(userDto));

        given(userRepository.findAll()).willReturn(list);
        List<UserDto> customerDtoList = userService.findAll();

        assertThat(customerDtoList).isNotNull();
        assertThat(customerDtoList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllUsers method (negative scenario)")
    @Test
    public void getAllUserListFailTestCase() {

        UserDto userDto1 = UserDto.builder()
                .emailId("anandK123@gmail.com")
                .userName("anandkk")
                .password("HELLO")
                .confirmPassword("HELLO")
                .mobileNo("8112233800")
                .userRole(UserRole.CUSTOMER).build();

        given(userRepository.findAll()).willReturn(Collections.emptyList());

        List<UserDto> allUser= userService.findAll();

        assertThat(allUser).isEmpty();
        assertThat(allUser.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getCustomerByEmailId method")
    @Test
    public void getUserByEmailIdTest() {
        given(userRepository.findByEmailId("ramesh123@gmail.com")).willReturn(Optional.ofNullable(userService.userDtoToEntity(userDto)));

        UserDto savedUser = userService.findByEmailId(userDto.getEmailId());

        assertThat(savedUser).isNotNull();

    }


}