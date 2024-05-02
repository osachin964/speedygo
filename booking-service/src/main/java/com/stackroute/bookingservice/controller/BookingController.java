package com.stackroute.bookingservice.controller;

import com.stackroute.bookingservice.dto.BookingDto;
import com.stackroute.bookingservice.dto.DistanceRequest;
import com.stackroute.bookingservice.dto.TransporterDetails;
import com.stackroute.bookingservice.entity.Booking;
import com.stackroute.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bookingService")
public class BookingController {

    @Autowired
    BookingService bookingService;
    BookingRepository bookingRepository;
    

    @PostMapping("/create-booking")
    public ResponseEntity<String> createBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }

    @GetMapping("/get-booking-by-customerId/{customerId}")
    public ResponseEntity<List<Booking>> getBookingByCustomer(@PathVariable("customerId") String customerId) {
        List<Booking> bookings = bookingService.getBookingByCustomer(customerId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/get-booking-by-transporterId/{transporterId}")
    public ResponseEntity<List<Booking>> getBookingByTransporter(@PathVariable("transporterId") String transporterId) {
        List<Booking> bookings = bookingService.getBookingByTransporter(transporterId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/get-booking-by-bookingId/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("bookingId") String bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }
    //    @PostMapping("/get-booking")
//    public ResponseEntity<Map<String, Object>> getBookings(@RequestBody BookingSearchRequest request){
//        List<Booking> bookings = bookingService.getBookings(request);
//        return ResponseEntity.ok(Map.of("bookingList",bookings));
//    }
    @PutMapping("/update-booking/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@RequestBody BookingDto bookingDto, @PathVariable("bookingId") String bookingId) {

        Booking updatedBooking = bookingService.updateBooking(bookingDto, bookingId);
        return ResponseEntity.ok(updatedBooking);
    }

    @PostMapping("/get-price/{transporterId}")
    public ResponseEntity<?> getPrice(@RequestBody DistanceRequest request, @PathVariable("transporterId") String transporterId) {
        Integer distance = bookingService.calculateDistance(request).intValue();
        Integer price = bookingService.getPricePerKmForTransporterId(transporterId);
        return ResponseEntity.ok(Map.of("distance", distance,
                "price", distance * price));
    }

    @PostMapping("/generate-quotation")
    public ResponseEntity<Map<String, Object>> generateQuotation(@RequestBody DistanceRequest request) throws Exception {
        List<TransporterDetails> transporterDetails = bookingService.generateQuotation(request);
        return ResponseEntity.ok(Map.of("TransporterList", transporterDetails));
    }

    @PutMapping("/update-booking-status/{bookingId}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable("bookingId") String bookingId, @PathVariable("status") String status) {
        Booking booking = bookingService.updateStatus(bookingId, status);
        return ResponseEntity.ok(booking);
    }
    @GetMapping("/view-status/{bookingId}")
    public ResponseEntity<?> viewBookingStatus(@PathVariable("bookingId") String bookingId){
        String meassage = bookingService.viewStatus(bookingId);
        return ResponseEntity.ok(Map.of("status",meassage));
    }
    @GetMapping("/get-all-bookings")
    public ResponseEntity<?> getAllBookings(){
        return bookingService.getAllBookings();
    }
//  '  @PostMapping("get-all-transporters")
//    public ResponseEntity<?> getAllTransporters(){
//        return bookingService.getAllTransporters();
//    }'

}
