package com.stackroute.reviewservice.repository;

import com.stackroute.reviewservice.entity.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class RepositoryTest {

    @Mock
    ReviewRepository reviewRepository;

    List<Review> reviews;

    @Test
    void findByBookingId()
    {
        String  bookingId  = "BK898976";
        reviews = reviewRepository.findByBookingId(bookingId);
        Mockito.when(reviewRepository.findByBookingId(bookingId)).thenReturn(reviews);
    }

    @Test
    void findByTransporterId()
    {
        String transporterId = "FastSpeed@gmail.com";
        reviews = reviewRepository.findByTransporterId(transporterId);
        Mockito.when(reviewRepository.findByTransporterId(transporterId)).thenReturn(reviews);
    }
}
