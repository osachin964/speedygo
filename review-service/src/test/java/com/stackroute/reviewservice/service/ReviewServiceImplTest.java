package com.stackroute.reviewservice.service;

import com.stackroute.reviewservice.dto.ReviewDto;
import com.stackroute.reviewservice.entity.Review;
import com.stackroute.reviewservice.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    private ReviewServiceImpl reviewServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        reviewServiceImplUnderTest = new ReviewServiceImpl();
        reviewServiceImplUnderTest.reviewRepository = mock(ReviewRepository.class);
    }



    @Test
    void testDeleteReview() {
        // Setup
        // Configure ReviewRepository.findById(...).
        final Review review1 = new Review();
        review1.setCustomerEmail("SakshiSharma@gmail.com");
        review1.setCustomerName("Sakshi Sharma");
        review1.setCompanyName("FastSpeed");
        review1.setTransporterId("FastSpeed@gmail.com");
        review1.setBookingId("BK600");
        review1.setRating(8);
        review1.setRemark("Service was Good");
        final Optional<Review> review = Optional.of(review1);
        when(reviewServiceImplUnderTest.reviewRepository.findById("SakshiSharma@gmail.com")).thenReturn(review);

        // Run the test
        final String result = reviewServiceImplUnderTest.deleteReview("SakshiSharma@gmail.com");

        // Verify the results
        assertThat(result).isEqualTo("Review Deleted Successfully");

    }


    @Test
    void testGetReviewByTransporter() {
        // Setup
        final List<ReviewDto> expectedResult = List.of(new ReviewDto("SakshiSharma@gmail.com", "Sakshi Sharma","FastSpeed@gmail.com","BK600", 8.0, "Service was Good"));

        // Configure ReviewRepository.findByCompanyName(...).
        final Review review = new Review();
        review.setCustomerEmail("SakshiSharma@gmail.com");
        review.setCustomerName("Sakshi Sharma");
//        review.setCompanyName("FastSpeed");
        review.setTransporterId("FastSpeed@gmail.com");
        review.setBookingId("BK600");
        review.setRating(8);
        review.setRemark("Service was Good");
        final List<Review> reviews = List.of(review);
        when(reviewServiceImplUnderTest.reviewRepository.findByTransporterId("FastSpeed@gmail.com")).thenReturn(reviews);

        // Run the test
        final List<ReviewDto> result = reviewServiceImplUnderTest.getReviewByTransporter("FastSpeed@gmail.com");

    }

    @Test
    void testGetReviewByTransporter_ReviewRepositoryReturnsNoItems() {
        // Setup
        when(reviewServiceImplUnderTest.reviewRepository.findByTransporterId("FastSpeed@gmail.com"))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<ReviewDto> result = reviewServiceImplUnderTest.getReviewByTransporter("FastSpeed@gmail.com");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetAverageRatingOfTransporter() {
        // Setup
        // Configure ReviewRepository.findByCompanyName(...).
        final Review review = new Review();
        review.setCustomerEmail("SakshiSharma@gmail.com");
        review.setCustomerName("Sakshi Sharma");
        review.setTransporterId("FastSpeed@gmail.com");
        review.setBookingId("BK600");
        review.setRating(8.0);
        review.setRemark("Service was Good");
        final List<Review> reviews = List.of(review);
        when(reviewServiceImplUnderTest.reviewRepository.findByTransporterId("FastSpeed@gmail.com")).thenReturn(reviews);
        // Run the test
        final Double result = reviewServiceImplUnderTest.getAverageRatingOfTransporter("FastSpeed@gmail.com");

        // Verify the results
        assertThat(result).isEqualTo(8.0, within(0.0001));
    }



}