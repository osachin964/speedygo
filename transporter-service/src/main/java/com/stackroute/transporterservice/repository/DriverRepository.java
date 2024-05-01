package com.stackroute.transporterservice.repository;


import com.stackroute.transporterservice.entity.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {

     Optional<Driver> findByDriverId(String driverId);
}
