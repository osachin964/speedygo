package com.stackroute.authenticationservice;

import com.stackroute.authenticationservice.service.UserService;
import com.stackroute.authenticationservice.dto.UserDto;
import com.stackroute.authenticationservice.entity.Role;
import com.stackroute.authenticationservice.entity.Users;
import com.stackroute.authenticationservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTests {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    void testLoadUserByUsername() {
        // Setup
        when(mockUserRepository.findByUsername("shivangis3107@gmail.com")).thenReturn(new Users("shivangis3107@gmail.com", "shiva#123", "CUSTOMER"));

        // Run the test
        final UserDetails result = userServiceUnderTest.loadUserByUsername("shivangis3107@gmail.com");

        // Verify the results
    }

    @Test
    void testLoadUserByUsername_UserRepositoryReturnsNull() {
        // Setup
        when(mockUserRepository.findByUsername("shivangis3107@gmail.com")).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> userServiceUnderTest.loadUserByUsername("shivangis3107@gmail.com"))
                .isInstanceOf(UsernameNotFoundException.class);
    }



    @Test
    void testSaveUser() {
        // Setup
        final UserDto user = new UserDto("shivangis3107@gmail.com", "shiva#123", Role.CUSTOMER);
        final Users expectedResult = new Users("shivangis3107@gmail.com", "shiva#123", "CUSTOMER");
        when(mockUserRepository.save(new Users("shivangis3107@gmail.com", "shiva#123", "CUSTOMER")))
                .thenReturn(new Users("shivangis3107@gmail.com", "shiva#123", "CUSTOMER"));

        // Run the test
        final Users result = userServiceUnderTest.saveUser(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUser() {
        // Setup
        final Users expectedResult = new Users("shivangis3107@gmail.com", "shiva#123", "CUSTOMER");
        when(mockUserRepository.findByUsername("shivangis3107@gmail.com")).thenReturn(new Users("shivangis3107@gmail.com", "shiva#123", "CUSTOMER"));

        // Run the test
        final Users result = userServiceUnderTest.getUser("shivangis3107@gmail.com");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
