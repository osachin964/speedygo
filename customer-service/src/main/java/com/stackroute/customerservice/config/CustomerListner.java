package com.stackroute.customerservice.config;

import com.stackroute.customerservice.dto.CustomerConsumerDto;
import com.stackroute.customerservice.dto.CustomerDto;
import com.stackroute.customerservice.service.CustomerService;
import com.stackroute.customerservice.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerListner {

    @Autowired
    CustomerService customerService;

    @RabbitListener(queues = AppConstants.CUSTOMER_QUEUE)
    public void listenUser(CustomerConsumerDto customerConsumerDto){
        if (null != customerConsumerDto.getEmailId() && null != customerConsumerDto.getUserName()) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setEmailId(customerConsumerDto.getEmailId());
            customerDto.setUserName(customerConsumerDto.getUserName());
            customerDto.setMobileNo(customerConsumerDto.getMobileNo());
            customerService.createCustomer(customerDto);
            log.info("message consumed and stored successfully in DB");
        }else {
            log.info("message consumed failed");
        }
    }

}
