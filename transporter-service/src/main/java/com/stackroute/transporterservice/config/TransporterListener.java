package com.stackroute.transporterservice.config;

import com.stackroute.transporterservice.dto.TransporterConsumerDto;
import com.stackroute.transporterservice.dto.TransporterDto;
import com.stackroute.transporterservice.service.TransporterService;
import com.stackroute.transporterservice.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransporterListener {

    @Autowired
    TransporterService transporterService;

    @RabbitListener(queues = AppConstants.TRANSPORTER_QUEUE)
    public void listenUser(TransporterConsumerDto transporterConsumerDto){
        if (null != transporterConsumerDto.getEmailId() && null != transporterConsumerDto.getUserName()) {
            TransporterDto transporterDto = new TransporterDto();
            transporterDto.setTransporterEmail(transporterConsumerDto.getEmailId());
            transporterDto.setUserName(transporterConsumerDto.getUserName());
            transporterService.createTransporter(transporterDto);

            log.info("message consumed and stored successfully in DB");
        }else {
            log.info("message consumed failed");
        }
    }
}
