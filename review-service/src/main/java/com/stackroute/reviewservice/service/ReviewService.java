package com.stackroute.reviewservice.service;

import com.stackroute.reviewservice.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    List<ReviewDto> getReviewByTransporter(String transporterId);

    Double getAverageRatingOfTransporter(String transporterId);

    ReviewDto addReview(ReviewDto reviewDto);

    ReviewDto updateReview(ReviewDto reviewDto, String customerEmail);

    String deleteReview(String customerEmail);

    List<ReviewDto> getReviewByBookingId(String bookingId);
}
