package com.stackroute.customerservice.service.impl;

import com.stackroute.customerservice.dto.CustomerDto;
import com.stackroute.customerservice.entity.Customer;
import com.stackroute.customerservice.exception.CustomerAlreadyExistException;
import com.stackroute.customerservice.exception.CustomerNotFoundException;
import com.stackroute.customerservice.repository.CustomerRepository;
import com.stackroute.customerservice.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CustomerRepository customerRepository;

    /**
     * @param customerDto -customer data transfer object
     * @return customerDto
     */
    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (customerRepository.existsById(customerDto.getEmailId())) {
            throw new CustomerAlreadyExistException("Customer already exist");
        }
        Customer customer = customerRepository.save(customerDtoToEntity(customerDto));
        return customerEntityToDto(customer);
    }

    /**
     * @param customerDto -customer data transfer object
     * @param email       - customer email
     * @return customerDto
     */
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, String email) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(email).orElseThrow(CustomerNotFoundException::new);

        customer.setEmailId(customerDto.getEmailId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setMobileNo(customerDto.getMobileNo());
        customer.setUserName(customerDto.getUserName());
        customer.setCity(customerDto.getCity());
        customer.setState(customerDto.getState());
        customer.setPincode(customerDto.getPincode());

        Customer updatedCustomer = customerRepository.save(customer);
        return customerEntityToDto(updatedCustomer);
    }

    /**
     * @return List<CustomerDto>
     */
    @Override
    public List<CustomerDto> getAllCustomer() {
        List<CustomerDto> customerDtos = new ArrayList<>();

        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            customerDtos.add(customerEntityToDto(customer));
        }
        return customerDtos;
    }


    /**
     * @param customerEmail - email of customer
     * @return CustomerDto
     */
    @Override
    public CustomerDto getCustomerByEmail(String customerEmail) {
        Customer customer = customerRepository.findById(customerEmail).orElseThrow(CustomerNotFoundException::new);
        return customerEntityToDto(customer);
    }

    /**
     * @param customerEmail -email of customer
     */
    @Override
    public String deleteCustomer(String customerEmail) {
        String response;
        if (customerRepository.existsById(customerEmail)) {
            customerRepository.deleteById(customerEmail);
            response = "Customer Account Deleted";
        } else {
            response = "Customer not found";
        }
        return response;
    }

    public Customer customerDtoToEntity(CustomerDto customerDto) {
        return new ModelMapper().map(customerDto, Customer.class);
    }

    public CustomerDto customerEntityToDto(Customer customer) {
        return new ModelMapper().map(customer, CustomerDto.class);
    }
}
