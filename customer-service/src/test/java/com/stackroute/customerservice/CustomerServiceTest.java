package com.stackroute.customerservice;

import com.stackroute.customerservice.dto.CustomerDto;
import com.stackroute.customerservice.entity.Customer;
import com.stackroute.customerservice.exception.CustomerAlreadyExistException;
import com.stackroute.customerservice.repository.CustomerRepository;
import com.stackroute.customerservice.service.impl.CustomerServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
 class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerDto customerDto;

    @BeforeEach
    public void setup() {
        customerDto = CustomerDto.builder()
                .emailId("ramesh123@gmail.com")
                .userName("rameshS")
                .firstName("Ramesh")
                .lastName("sharma")
                .mobileNo("8800112233")
                .city("Bengaluru")
                .state("Karnataka")
                .pincode("560040").build();
    }

    @DisplayName("JUnit test for saveCustomer method")
    @Test
     void saveCustomerTest() {
        given(customerRepository.existsById(customerDto.getEmailId()))
                .willReturn(false);

        Customer entity = customerService.customerDtoToEntity(customerDto);
        given(customerRepository.save(entity)).willReturn(entity);


        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        assertThat(savedCustomer).isNotNull();
    }

    @DisplayName("JUnit test for saveCustomer method which throws exception")
    @Test
     void saveCustomerTestCaseFail() {
        given(customerRepository.existsById(customerDto.getEmailId()))
                .willReturn(true);


        assertThrows(CustomerAlreadyExistException.class, () -> customerService.createCustomer(customerDto));

        verify(customerRepository, never()).save(customerService.customerDtoToEntity(customerDto));

    }

    @DisplayName("JUnit test for getAllCustomers method")
    @Test
     void getAllCustomerListTest() {

        CustomerDto customerDto1 = CustomerDto.builder()
                .emailId("anandK123@gmail.com")
                .userName("anandkk")
                .firstName("anand")
                .lastName("kanti")
                .mobileNo("8112233800")
                .city("Gulbarga")
                .state("Karnataka")
                .pincode("585105").build();

        List<Customer> list = new ArrayList<>();
        list.add(customerService.customerDtoToEntity(customerDto));
        list.add(customerService.customerDtoToEntity(customerDto1));

        given(customerRepository.findAll()).willReturn(list);
        List<CustomerDto> customerDtoList = customerService.getAllCustomer();

        assertThat(customerDtoList).isNotNull();
        assertThat(customerDtoList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllCustomers method (negative scenario)")
    @Test
     void getAllCustomerListFailTestCase() {

        CustomerDto customerDto1 = CustomerDto.builder()
                .emailId("anandK123@gmail.com")
                .userName("anandkk")
                .firstName("anand")
                .lastName("kanti")
                .mobileNo("8112233800")
                .city("Gulbarga")
                .state("Karnataka")
                .pincode("585105").build();

        given(customerRepository.findAll()).willReturn(Collections.emptyList());

        List<CustomerDto> allCustomer = customerService.getAllCustomer();

        assertThat(allCustomer).isEmpty();
        assertThat(allCustomer.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getCustomerByEmailId method")
    @Test
     void getCustomerByEmailIdTest() {
        given(customerRepository.findById("ramesh123@gmail.com")).willReturn(Optional.ofNullable(customerService.customerDtoToEntity(customerDto)));

        CustomerDto savedCustomer = customerService.getCustomerByEmail(customerDto.getEmailId());

        assertThat(savedCustomer).isNotNull();

    }

    @DisplayName("JUnit test for updateCustomer method")
    @Test
     void updateCustomerTest() {

        CustomerDto customerDto1 = CustomerDto.builder()
                .emailId("anandK123@gmail.com")
                .userName("anandkk")
                .firstName("anand")
                .lastName("kanti")
                .mobileNo("8112233800")
                .city("Gulbarga")
                .state("Karnataka")
                .pincode("585105").build();
        Customer customer = Customer.builder()
                .emailId("anandK123@gmail.com")
                .userName("anandkk")
                .firstName("anand")
                .lastName("kanti")
                .mobileNo("8112233800")
                .city("Gulbarga")
                .state("Karnataka")
                .pincode("585105").build();

        given(customerRepository.findById("anandK123@gmail.com")).willReturn(Optional.ofNullable(customerService.customerDtoToEntity(customerDto)));
        given(customerRepository.save(any())).willReturn(customer);
        assertEquals("anandK123@gmail.com", customerService.updateCustomer(customerDto1, "anandK123@gmail.com").getEmailId());

    }

//    @DisplayName("JUnit test for deleteCustomer method")
//    @Test
//     void deleteCustomerTest() {
//        String emailId = "anandK123@gmail.com";
//
//        willDoNothing().given(customerRepository).deleteById(emailId);
//
//
//        customerService.deleteCustomer(emailId);
//
//        verify(customerRepository, times(1)).deleteById(emailId);
//    }
}
