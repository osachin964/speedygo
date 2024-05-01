package com.stackroute.transporterservice.controller;


import com.stackroute.transporterservice.config.LoggerConfig;
import com.stackroute.transporterservice.dto.DriverDto;
import com.stackroute.transporterservice.dto.TransporterDto;
import com.stackroute.transporterservice.entity.Transporter;
import com.stackroute.transporterservice.service.TransporterService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transporterService")
public class TransporterController {

    private Logger logger = LoggerConfig.getLogger(TransporterController.class);


    @Autowired
    private TransporterService transporterService;


    @PostMapping("/saveTransporter")
    public ResponseEntity<TransporterDto> createTransporter(@Valid @RequestBody TransporterDto transporterDto){

        String methodName = "createTransporter()";
        logger.info(methodName + "called");

        TransporterDto createTransporterDto = this.transporterService.createTransporter(transporterDto);
        return new ResponseEntity<>(createTransporterDto, HttpStatus.CREATED);
    }



    @GetMapping("/getTransporter/{transporterEmail}")
    public ResponseEntity<TransporterDto> getTransporterByEmail(@PathVariable String transporterEmail){

        String methodName = "getTransporterByEmail()";
        logger.info(methodName + "called");

        return new ResponseEntity<>(this.transporterService.getTransporterByEmail(transporterEmail), HttpStatus.OK);
    }



    @GetMapping("/getAllTransporters")
    public ResponseEntity<List<Transporter>> getAllTransporter() {

        String methodName = "getAllTransporter()";
        logger.info(methodName + "called");

        return new ResponseEntity<>(this.transporterService.getAllTransporter(), HttpStatus.OK);
    }



    @PutMapping("/updateTransporter/{transporterEmail}")
    public ResponseEntity<TransporterDto> updateTransporter(@Valid @RequestBody TransporterDto transporterDto, @PathVariable String transporterEmail) {

        String methodName = "getAllTransporter()";
        logger.info(methodName + "called");

        TransporterDto updatedTransporter = this.transporterService.updateTransporter(transporterDto, transporterEmail);
        return ResponseEntity.ok(updatedTransporter);
    }



    @DeleteMapping("/deleteTransporter/{transporterEmail}")
    public ResponseEntity<String> deleteTransporter(@PathVariable String transporterEmail) {

        String methodName = "deleteTransporter()";
        logger.info(methodName + "called");

        String msg = this.transporterService.deleteTransporter(transporterEmail);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }



    @PostMapping("/saveDriver")
    public ResponseEntity<DriverDto> createDriver(@Valid @RequestBody DriverDto driverDto) {

        String methodName = "createDriver()";
        logger.info(methodName + "called");

        return new ResponseEntity<DriverDto>(this.transporterService.createDriver(driverDto),HttpStatus.CREATED);
    }



    @GetMapping("/getDriverById/{driverId}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable String driverId) {

        String methodName = "getDriverById()";
        logger.info(methodName + "called");

        return new ResponseEntity<>(this.transporterService.getDriverById(driverId), HttpStatus.OK);
    }



    @GetMapping("/getAllDrivers")
    public ResponseEntity<List<DriverDto>> getAllDriver() {

        String methodName = "getAllDriver()";
        logger.info(methodName + "called");

       return new ResponseEntity<>(this.transporterService.getAllDriver(), HttpStatus.OK);
    }


   // @ApiOperation(value = "Update Drivers Info")
    @PutMapping("/updateDriver/{driverId}")
    public ResponseEntity<DriverDto> updateDriver(@Valid @RequestBody DriverDto driverDto,@PathVariable String driverId) {

        String methodName = "updateDriver()";
        logger.info(methodName + "called");

        DriverDto updatedDriver = this.transporterService.updateDriver(driverDto, driverId);
        return ResponseEntity.ok(updatedDriver);
    }



    @DeleteMapping("/deleteDriver/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable String driverId){

        String methodName = "deleteDriver()";
        logger.info(methodName + "called");

        String msg = this.transporterService.deleteDriver(driverId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/getTop3")
    public ResponseEntity<List<TransporterDto>> getTop3() {

        String methodName = "getTop3()";
        logger.info(methodName + " called");

        return new ResponseEntity<>(this.transporterService.getTop3(), HttpStatus.OK);
    }

    @GetMapping("/getRandomDriver/{transporterEmail}")
    public DriverDto getRandomDriver(@PathVariable String transporterEmail){

        String methodName = "getRandomDrivers()";
        logger.info(methodName + " called");
        DriverDto randomDriver = transporterService.getRandomDriver(transporterEmail);
        return randomDriver;
//        return new ResponseEntity<>(this.transporterService.getRandomDriver(transporterEmail), HttpStatus.OK);
    }

}

