package com.stackroute.bookingservice.service;

import com.stackroute.bookingservice.dto.BookingDto;
import com.stackroute.bookingservice.dto.DistanceRequest;
import com.stackroute.bookingservice.dto.TransporterDetails;
import com.stackroute.bookingservice.entity.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public interface BookingService {



     ResponseEntity<String> createBooking(BookingDto bookingDto) ;
    // List<Booking> getBookings(BookingSearchRequest request);
     Double calculateDistance(DistanceRequest request);
     List<TransporterDetails> generateQuotation(DistanceRequest request);

     Booking updateBooking(BookingDto booking, String bookingId);

     List<Booking> getBookingByCustomer(String customerId);

     List<Booking> getBookingByTransporter(String transporterId);

     Booking getBookingById(String bookingId);

    Booking updateStatus(String bookingId, String status);

    String viewStatus(String bookingId);

    ResponseEntity<?> getAllBookings();

    Integer getPricePerKmForTransporterId(String transporterId);

    ResponseEntity<?> getAllTransporters();
}
