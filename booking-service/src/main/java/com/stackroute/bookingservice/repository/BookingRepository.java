package com.stackroute.bookingservice.repository;
import com.stackroute.bookingservice.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository  extends MongoRepository<Booking, String> {
    List<Booking> findAllByTransporterId(String transporterId);
    List<Booking> findAllByCustomerId(String customerId);

}
