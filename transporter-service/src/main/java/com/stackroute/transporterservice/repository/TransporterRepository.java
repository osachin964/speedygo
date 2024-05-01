package com.stackroute.transporterservice.repository;


import com.stackroute.transporterservice.entity.Transporter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface TransporterRepository  extends MongoRepository<Transporter, String> {


      Optional<Transporter> findByTransporterEmail(String transporterEmail);
      List<Transporter> findTop3ByOrderByPricePerKmAsc();


}
