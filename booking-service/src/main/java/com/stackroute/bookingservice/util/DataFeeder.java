package com.stackroute.bookingservice.util;

import com.stackroute.bookingservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataFeeder implements CommandLineRunner {

    @Autowired
    BookingRepository bookingRepository;
    @Override
    public void run(String... args) throws Exception {
        bookingRepository.deleteAll();
       // bookingRepository.save(Booking.builder().bookingId("C01").customerName("cnmae").build());
    }
}
