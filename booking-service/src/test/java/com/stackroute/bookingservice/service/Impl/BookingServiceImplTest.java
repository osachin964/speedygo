package com.stackroute.bookingservice.service.Impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.stackroute.bookingservice.dto.BookingDto;
import com.stackroute.bookingservice.dto.StatusEmailDto;
import com.stackroute.bookingservice.entity.Booking;
import com.stackroute.bookingservice.repository.BookingRepository;
import com.stackroute.bookingservice.service.BookingService;
import com.stackroute.bookingservice.service.Impl.BookingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @InjectMocks
    private BookingServiceImpl bookingService;

    private BookingDto bookingDto;
    private Booking booking;

//    @Before
//    public void setup() {
//        bookingDto = new BookingDto();
//        bookingDto.setCustomerName("John Doe");
//        bookingDto.setTransporterId("T123");
//        bookingDto.setPickupLocation("560066");
//        bookingDto.setDropLocation("560001");
//        bookingDto.setCustomerId("johndoe@gmail.com");
//        bookingDto.setCustomerMobileNo("1234567890");
//
//        booking = new Booking();
//        booking.setBookingId("B123");
//        booking.setCustomerName("John Doe");
//        booking.setTransporterId("T123");
//        booking.setPickupLocation("Seattle");
//        booking.setDropLocation("Los Angeles");
//        booking.setCustomerId("johndoe@gmail.com");
//        booking.setCustomerMobileNo("1234567890");
//        booking.setStatus("Created");
//        booking.setTotalPrice(1000.0);
//
//        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
//        RestTemplate restTemplate = new RestTemplate();
//        String uri = "http://localhost:8090/transporterService/getTransporter/" + booking.getTransporterId();
//        when(restTemplate.getForObject(uri, String.class)).thenReturn(any());
//    }

    //    @Test
//    public void testCreateBooking_success() {
//
//
//        ResponseEntity<String> response = bookingService.createBooking(bookingDto);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
    @Before
    public void setup() {
        bookingDto = new BookingDto();
        bookingDto.setCustomerId("1");
        bookingDto.setServiceType("ServiceType");
        bookingDto.setCustomerName("CustomerName");
        bookingDto.setDropLocation("DropLocation");
        bookingDto.setPickupLocation("PickupLocation");
        bookingDto.setCustomerMobileNo("1234567890");
        bookingDto.setTransporterId("TransporterId");

        booking = new Booking();
        booking.setBookingId("1");
        booking.setCustomerId("1");
        booking.setServiceType("ServiceType");
        booking.setCustomerName("CustomerName");
        booking.setDropLocation("DropLocation");
        booking.setPickupLocation("PickupLocation");
        booking.setCustomerMobileNo("1234567890");
        booking.setTransporterId("TransporterId");
    }

    @Test
    public void testUpdateBooking_Success() {
        when(bookingRepository.findById(anyString())).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = bookingService.updateBooking(bookingDto, "1");
        assertNotNull(result);
        assertEquals("1", result.getBookingId());
        assertEquals("1", result.getCustomerId());
        assertEquals("ServiceType", result.getServiceType());
        assertEquals("CustomerName", result.getCustomerName());
        assertEquals("DropLocation", result.getDropLocation());
        assertEquals("PickupLocation", result.getPickupLocation());
        assertEquals("1234567890", result.getCustomerMobileNo());
        assertEquals("TransporterId", result.getTransporterId());
    }
    @Test
    public void testGetBookingByCustomer() {
        // Arrange
        String customerId = "cust1";
        List<Booking> expected = Arrays.asList(
                new Booking(),
                new Booking()
        );

        when(bookingRepository.findAllByCustomerId(customerId)).thenReturn(expected);

        // Act
        List<Booking> actual = bookingService.getBookingByCustomer(customerId);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBookingByTransporter() {
        // Arrange
        String transporterId = "trans1";
        List<Booking> expected = Arrays.asList(
                new Booking(),
                new Booking()
        );

        when(bookingRepository.findAllByTransporterId(transporterId)).thenReturn(expected);

        // Act
        List<Booking> actual = bookingService.getBookingByTransporter(transporterId);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBookingById() {
        // Arrange
        String bookingId = "book1";
        Booking expected = new Booking();

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(expected));

        // Act
        Booking actual = bookingService.getBookingById(bookingId);

        // Assert
        assertEquals(expected, actual);
    }


    @Test
    public void updateStatusTest() {
        // Arrange
        String bookingId = "booking123";
        String status = "CONFIRMED";
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setStatus("PENDING");

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);


        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(StatusEmailDto.class));

        // Act
        Booking updatedBooking = bookingService.updateStatus(bookingId, status);

        // Assert
        assertEquals(status, updatedBooking.getStatus());
        verify(bookingRepository).findById(bookingId);
        verify(bookingRepository).save(booking);
        verify(rabbitTemplate).convertAndSend(anyString(),anyString(), any(StatusEmailDto.class));
    }

    @Test
    public void viewStatusTest() {

        // given
        Booking booking = new Booking();
        booking.setBookingId("789");
        booking.setCustomerId("012");
        booking.setStatus("completed");
        bookingRepository.save(booking);
        when(bookingRepository.findById("789")).thenReturn(Optional.of(booking));
//        when(bookingRepository.save(booking)).thenReturn(booking);
//        when(bookingService.viewStatus("789")).thenReturn(booking.getStatus());

        // when
        String status = bookingService.viewStatus("789");

        // then
        assertEquals("completed", status);
    }

}