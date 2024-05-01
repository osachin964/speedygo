package com.stackroute.transporterservice.repository;

import com.stackroute.transporterservice.entity.Transporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@DataMongoTest
class TransporterRepositoryTest {

    @Mock
    TransporterRepository transporterRepository;
    @Autowired
    Optional<Transporter> transporter;

    @Test
    void findByTransporterEmail()
    {
        String requestEmail = "RiyaShiva@gmail.com";
        transporter = transporterRepository.findByTransporterEmail(requestEmail);
        Mockito.when(transporterRepository.findByTransporterEmail(requestEmail)).thenReturn(transporter);
    }


}