package com.stackroute.customerservice.controller;

import com.stackroute.customerservice.dto.CustomerDto;
import com.stackroute.customerservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/customerService")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping("/saveCustomerDetails")
    public ResponseEntity<?> saveCustomerDetails(@Valid @RequestBody CustomerDto customerDto) {
        logger.info("INSIDE Save Customer controller");
        logger.info("Customer Details Received: {}",customerDto.toString());

        CustomerDto createCustomerDto = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(createCustomerDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateCustomer/{email}")
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable("email") String email) {
        logger.info("INSIDE Update Customer controller");
        logger.info("Customer Email to update: {}",email);
        logger.info("Customer Details Received: {}",customerDto.toString());

        CustomerDto updatedCustomerDto = customerService.updateCustomer(customerDto, email);
        return ResponseEntity.ok(updatedCustomerDto);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        logger.info("Inside getAllCustomer controller");
        return ResponseEntity.ok(this.customerService.getAllCustomer());
    }

    @GetMapping("/getCustomer/{email}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String email) {
        logger.info("Inside getCustomer controller");
        logger.info("Customer email to fetch details: "+email);
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @DeleteMapping("/deleteCustomer/{email}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String email) {
        logger.info("Inside deleteCustomer controller");
        logger.info("Customer email to delete : "+email);
        String response = customerService.deleteCustomer(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
