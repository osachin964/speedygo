package com.stackroute.customerservice.service;

import com.stackroute.customerservice.dto.CustomerDto;
import com.stackroute.customerservice.exception.CustomerAlreadyExistException;
import com.stackroute.customerservice.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto) throws CustomerAlreadyExistException;

    CustomerDto updateCustomer(CustomerDto customerDto, String email);

    List<CustomerDto> getAllCustomer();

    CustomerDto getCustomerByEmail(String customerEmail) throws CustomerNotFoundException;

    String deleteCustomer(String customerEmail);

}
