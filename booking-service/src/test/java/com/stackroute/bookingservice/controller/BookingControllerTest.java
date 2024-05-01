package com.stackroute.bookingservice.controller;
import com.stackroute.bookingservice.dto.BookingDto;
import com.stackroute.bookingservice.dto.DistanceRequest;
import com.stackroute.bookingservice.dto.TransporterDetails;
import com.stackroute.bookingservice.entity.Booking;
import com.stackroute.bookingservice.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BookingController bookingController;

    @Test
    public void createBookingTest() {
        BookingDto bookingDto = new BookingDto();
        when(bookingService.createBooking(bookingDto)).thenReturn(ResponseEntity.ok("Booking created successfully"));
        ResponseEntity<String> response = bookingController.createBooking(bookingDto);
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), "Booking created successfully");
    }

    @Test
    public void getBookingByCustomerTest() {
        String customerId = "123";
        List<Booking> bookings = new ArrayList<>();
        when(bookingService.getBookingByCustomer(customerId)).thenReturn(bookings);
        ResponseEntity<List<Booking>> response = bookingController.getBookingByCustomer(customerId);
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), bookings);
    }
    @Test
    public void testGetBookingByTransporter() {
        List<Booking> expectedBookings = Arrays.asList(new Booking(), new Booking());
        when(bookingService.getBookingByTransporter("transporterId")).thenReturn(expectedBookings);

        ResponseEntity<List<Booking>> actualResponse = bookingController.getBookingByTransporter("transporterId");
        List<Booking> actualBookings = actualResponse.getBody();

        assertEquals(expectedBookings, actualBookings);
    }

    @Test
    public void testGetBookingById() {
        Booking expectedBooking = new Booking();
        when(bookingService.getBookingById("bookingId")).thenReturn(expectedBooking);

        ResponseEntity<Booking> actualResponse = bookingController.getBookingById("bookingId");
        Booking actualBooking = actualResponse.getBody();

        assertEquals(expectedBooking, actualBooking);
    }
    @Test
    void testUpdateBooking() throws Exception {
        BookingDto bookingDto = new BookingDto();
        //set bookingDto properties
        Booking updatedBooking = new Booking();
        //set updatedBooking properties
        when(bookingService.updateBooking(bookingDto, "bookingId")).thenReturn(updatedBooking);

//        mockMvc.perform(put("/update-booking/bookingId")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(bookingDto)))
//                .andExpect(status().isOk());
        ResponseEntity<Booking> actualResponse = bookingController.updateBooking(bookingDto,"bookingId");

        verify(bookingService, times(1)).updateBooking(bookingDto, "bookingId");
    }

    @Test
    void testGetPrice() throws Exception {
        DistanceRequest request = new DistanceRequest(460661L,560066L);
        when(bookingService.calculateDistance(request)).thenReturn(200.00);
        when(bookingService.getPricePerKmForTransporterId("transporterId")).thenReturn(5);
        ResponseEntity<?> response = bookingController.getPrice(request,"transporterId");
        assertEquals(response.getStatusCodeValue(), 200);

    }
    //
    @Test
    void testGenerateQuotation() throws Exception {
        DistanceRequest request = new DistanceRequest(460661L,560066L);
        List<TransporterDetails> transporterDetails = new ArrayList<>();
        when(bookingService.generateQuotation(request)).thenReturn(transporterDetails);
        ResponseEntity<?> response = bookingController.generateQuotation(request);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody(),Map.of("TransporterList", transporterDetails));

    }


}

