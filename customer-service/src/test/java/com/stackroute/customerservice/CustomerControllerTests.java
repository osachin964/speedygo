package com.stackroute.customerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.customerservice.controller.CustomerController;
import com.stackroute.customerservice.dto.CustomerDto;
import com.stackroute.customerservice.exception.CustomerNotFoundException;
import com.stackroute.customerservice.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createCustomerTest() throws Exception {

        CustomerDto customerDto = CustomerDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .firstName("Ramesh")
                .lastName("sharma")
                .mobileNo("8800112233")
                .city("Bengaluru")
                .state("Karnataka")
                .pincode("560040").build();
        given(customerService.createCustomer(any(CustomerDto.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/customerService/saveCustomerDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(customerDto.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(customerDto.getLastName())))
                .andExpect(jsonPath("$.emailId",
                        is(customerDto.getEmailId())));
    }

    @Test
    public void updateCustomerTest() throws Exception {
        String emailId = "ramesh123@gmail.com";

        CustomerDto updatedCustomerDto = CustomerDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("anandkk")
                .firstName("anand")
                .lastName("kanti")
                .mobileNo("8112233800")
                .city("Gulbarga")
                .state("Karnataka")
                .pincode("585105").build();

        given(customerService.updateCustomer(any(CustomerDto.class), anyString())).
                willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/customerService/updateCustomer/{email}", emailId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomerDto)));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedCustomerDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedCustomerDto.getLastName())))
                .andExpect(jsonPath("$.emailId", is(updatedCustomerDto.getEmailId())));
    }

    @Test
    public void updateCustomerTestCaseFail() throws Exception {

        String emailId = "rachit.batgeri@gmail.com";
        CustomerDto customerDto = CustomerDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .firstName("Ramesh")
                .lastName("sharma")
                .mobileNo("8800112233")
                .city("Bengaluru")
                .state("Karnataka")
                .pincode("560040").build();

        Mockito.when(customerService.updateCustomer(customerDto, emailId)).thenThrow(new CustomerNotFoundException());

        mockMvc.perform(put("/customerService/updateCustomer/{email}", emailId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllCustomerTest() throws Exception {

        List<CustomerDto> listOfCustomers = new ArrayList<>();
        listOfCustomers.add(CustomerDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .firstName("Ramesh")
                .lastName("sharma")
                .mobileNo("8800112233")
                .city("Bengaluru")
                .state("Karnataka")
                .pincode("560040").build());
        listOfCustomers.add(CustomerDto.builder()
                .emailId("anandK123@gmail.com")
                .userName("anandkk")
                .firstName("anand")
                .lastName("kanti")
                .mobileNo("8112233800")
                .city("Gulbarga")
                .state("Karnataka")
                .pincode("585105").build());
        given(customerService.getAllCustomer()).willReturn(listOfCustomers);

        ResultActions response = mockMvc.perform(get("/customerService/getAllCustomers"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfCustomers.size())));
    }

    @Test
    public void getCustomerByEmailId() throws Exception{

        String emailId = "ramesh123@gmail.com";
        CustomerDto customerDto = CustomerDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .firstName("Ramesh")
                .lastName("sharma")
                .mobileNo("8800112233")
                .city("Bengaluru")
                .state("Karnataka")
                .pincode("560040").build();
        given(customerService.getCustomerByEmail(emailId)).willReturn(customerDto);


        ResultActions response = mockMvc.perform(get("/customerService/getCustomer/{email}", emailId));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(customerDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(customerDto.getLastName())))
                .andExpect(jsonPath("$.emailId", is(customerDto.getEmailId())));

    }


    @Test
    public void getCustomerByEmailIdFailCase() throws Exception{
        // given - precondition or setup
        String emailId = "anandK@gmail.com";
        CustomerDto customerDto = CustomerDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .firstName("Ramesh")
                .lastName("sharma")
                .mobileNo("8800112233")
                .city("Bengaluru")
                .state("Karnataka")
                .pincode("560040").build();
        given(customerService.getCustomerByEmail(emailId)).willThrow(new CustomerNotFoundException("customer not found"));


        ResultActions response = mockMvc.perform(get("/customerService/getCustomer/{email}", emailId));
        System.out.println(response);

        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void deleteByEmailId() throws Exception{
        // given - precondition or setup
        String emailId = "anandK@gmail.com";
        given(customerService.deleteCustomer(emailId)).willReturn("Customer Account Deleted");

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/customerService/deleteCustomer/{email}", emailId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

}
