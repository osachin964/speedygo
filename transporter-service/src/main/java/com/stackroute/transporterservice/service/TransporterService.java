package com.stackroute.transporterservice.service;



import com.stackroute.transporterservice.dto.DriverDto;
import com.stackroute.transporterservice.dto.TransporterDto;
import com.stackroute.transporterservice.entity.Transporter;
import com.stackroute.transporterservice.exception.DriverAlreadyExist;
import com.stackroute.transporterservice.exception.DriverNotFound;
import com.stackroute.transporterservice.exception.TransporterAlreadyExist;
import com.stackroute.transporterservice.exception.TransporterNotFound;

import java.util.List;

public interface TransporterService {

    TransporterDto createTransporter(TransporterDto transporter) throws TransporterAlreadyExist;

    TransporterDto getTransporterByEmail(String transporterEmail) throws TransporterNotFound;

     List<Transporter> getAllTransporter();
    TransporterDto updateTransporter(TransporterDto transporter, String transporterEmail) throws TransporterNotFound, DriverNotFound;

   String deleteTransporter(String transporterEmail) ;

    DriverDto createDriver(DriverDto driver) throws DriverAlreadyExist, DriverAlreadyExist;

    DriverDto updateDriver(DriverDto driverDto, String driverId) throws DriverNotFound;

   String deleteDriver(String driverId);

    DriverDto getDriverById(String driverId) throws DriverNotFound;

    List<DriverDto> getAllDriver();

    List<TransporterDto> getTop3();
    DriverDto getRandomDriver(String transporterEmail);




}
