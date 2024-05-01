package com.stackroute.reviewservice.service;

import com.stackroute.reviewservice.config.LoggerConfig;
import com.stackroute.reviewservice.dto.ReviewDto;
import com.stackroute.reviewservice.entity.Review;
import com.stackroute.reviewservice.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final Logger logger = LoggerConfig.getLogger(ReviewServiceImpl.class);

    @Autowired
    ReviewRepository reviewRepository;


    @Override
    public List<ReviewDto> getReviewByTransporter(String transporterId) {

        String methodName = "getReviewByTransporter()";
        logger.info(methodName + "called");

        List<ReviewDto> reviewDtoList = new ArrayList<>();
            List<Review> reviews = reviewRepository.findByTransporterId(transporterId);
            for (Review review : reviews) {
                reviewDtoList.add(ReviewEntityToDto(review));
            }
            return reviewDtoList;

    }

    @Override
    public Double getAverageRatingOfTransporter(String transporterId) {

        String methodName = "getAverageRatingOfTransporter()";
        logger.info(methodName + "called");

        int sum=0;
        double avgRating=0;

        List<Review> reviews = reviewRepository.findByTransporterId(transporterId);

        int length = reviews.size();
        if(reviews.isEmpty()){
            return 0.00;
        }
        for (Review review : reviews) {
            sum+=review.getRating();

        }
        avgRating=(double)sum/length;

        return avgRating;
    }


    @Override
    public ReviewDto addReview(ReviewDto reviewDto) {

        String methodName = "addReview()";
        logger.info(methodName + "called");

        Review review = reviewRepository.save(ReviewDtoToEntity(reviewDto));

        return ReviewEntityToDto(review);
    }

    @Override
    public ReviewDto updateReview(ReviewDto reviewDto, String customerEmail) {

        String methodName = "updateReview()";
        logger.info(methodName + "called");

        Review review = this.reviewRepository.findById(customerEmail).get();
        review.setCustomerName(reviewDto.getCustomerEmail());
        review.setTransporterId(review.getTransporterId());
        review.setRemark(reviewDto.getRemark());
        review.setRating(reviewDto.getRating());


        Review updatedReview= this.reviewRepository.save(review);
        ReviewDto reviewDto1 = this.ReviewEntityToDto(updatedReview);

        return reviewDto1;

    }

    @Override
    public String deleteReview(String customerEmail) {

        String methodName = "deleteReview()";
        logger.info(methodName + "called");

        Review review = this.reviewRepository.findById(customerEmail).get();
        this.reviewRepository.delete(review);
        return "Review Deleted Successfully";
    }

    @Override
    public List<ReviewDto> getReviewByBookingId(String bookingId) {
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        List<Review> reviews = reviewRepository.findByBookingId(bookingId);
        for (Review review : reviews) {
            reviewDtoList.add(ReviewEntityToDto(review));
        }
        return reviewDtoList;
    }

    private Review ReviewDtoToEntity(ReviewDto reviewDto)
    {
        return new ModelMapper().map(reviewDto, Review.class);
    }


    private ReviewDto ReviewEntityToDto(Review review)
    {
        return new ModelMapper().map(review, ReviewDto.class);
    }
}
