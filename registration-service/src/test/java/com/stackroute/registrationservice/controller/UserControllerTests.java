package com.stackroute.registrationservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.stackroute.registrationservice.dto.UserDto;
import com.stackroute.registrationservice.exception.IdNotFoundException;
import com.stackroute.registrationservice.model.UserRole;
import com.stackroute.registrationservice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @MockBean
    private RabbitTemplate template;

    private static ObjectMapper mapper = new ObjectMapper();



    @Test
    public void getAllUserTest() throws Exception {

        List<UserDto> listOfUser = new ArrayList<>();
        listOfUser.add(UserDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .password("HELLO")
                .confirmPassword("HELLO")
                .mobileNo("8800112233")
                .userRole(UserRole.CUSTOMER).build());
        listOfUser.add(UserDto.builder()
                .emailId("anandK123@gmail.com")
                .userName("anand")
                .password("HELLO")
                .confirmPassword("HELLO")
                .mobileNo("8112233800")
                .userRole(UserRole.TRANSPORTER).build());
        given(userService.findAll()).willReturn(listOfUser);

        ResultActions response = mockMvc.perform(get("/registrationService/getAllUsers"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfUser.size())));
    }

    @Test
    public void saveUserTest() throws Exception {

        UserDto userDto = UserDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .password("HELLO")
                .confirmPassword("HELLO")
                .mobileNo("8800112233")
                .userRole(UserRole.CUSTOMER).build();
        given(userService.saveUser(any(UserDto.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/registrationService/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName",
                        is(userDto.getUserName())))
                .andExpect(jsonPath("$.emailId",
                        is(userDto.getEmailId())));
    }


    @Test
    public void getUserByEmailId() throws Exception{

        String emailId = "ramesh123@gmail.com";
        UserDto userDto = UserDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .password("HELLO")
                .confirmPassword("HELLO")
                .mobileNo("8800112233")
                .userRole(UserRole.CUSTOMER).build();
        given(userService.findByEmailId(emailId)).willReturn(userDto);


        ResultActions response = mockMvc.perform(get("/registrationService/getUser/{email}", emailId));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.userName", is(userDto.getUserName())))
                .andExpect(jsonPath("$.emailId", is(userDto.getEmailId())));

    }


    @Test
    public void getUserByEmailIdFailCase() throws Exception{
        // given - precondition or setup
        String emailId = "anandK@gmail.com";
        UserDto customerDto = UserDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .password("HELLO")
                .confirmPassword("HELLO")
                .mobileNo("8800112233")
                .userRole(UserRole.CUSTOMER).build();
        given(userService.findByEmailId(emailId)).willThrow(new IdNotFoundException("Id not found"));


        ResultActions response = mockMvc.perform(get("/registrationService/getUser/{email}", emailId));
        System.out.println(response);

        response.andExpect(status().isNotFound())
                .andDo(print());

    }

}
