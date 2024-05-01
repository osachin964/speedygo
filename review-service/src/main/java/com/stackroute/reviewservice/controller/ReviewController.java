package com.stackroute.reviewservice.controller;


import com.stackroute.reviewservice.config.LoggerConfig;
import com.stackroute.reviewservice.dto.ReviewDto;
import com.stackroute.reviewservice.service.ReviewService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reviewService")
public class ReviewController {

    private Logger logger = LoggerConfig.getLogger(ReviewController.class);

    @Autowired
    ReviewService reviewService;

    @GetMapping("/getReview/{transporterId}")
    public ResponseEntity<List<ReviewDto>> getReviewByTransporter(@PathVariable("transporterId") String transporterId){

        String methodName = "getReviewByTransporter()";
        logger.info(methodName + "called");

        List<ReviewDto>  getReviewByTransporter= this.reviewService.getReviewByTransporter(transporterId);
        return new ResponseEntity<>(getReviewByTransporter, HttpStatus.OK);
    }
    @GetMapping("/getReviewByBookingId/{bookingId}")
    public ResponseEntity<List<ReviewDto>> getReviewByBooking(@PathVariable("bookingId") String bookingId){

        String methodName = "getReviewByTransporter()";
        logger.info(methodName + "called");

        List<ReviewDto>  getReviewByBooking= this.reviewService.getReviewByBookingId(bookingId);
        return new ResponseEntity<>(getReviewByBooking, HttpStatus.OK);
    }



    @GetMapping("/getRating/{transporterId}")
    public Double getAverageRatingOfTransporter(@PathVariable("transporterId") String transporterId){

        String methodName = "getAverageRatingOfTransporter()";
        logger.info(methodName + "called");

        Double  getAverageRatingOfTransporter= this.reviewService.getAverageRatingOfTransporter(transporterId);
        return getAverageRatingOfTransporter;
    }

    @PostMapping("/saveReview")
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewDto reviewDto){

        String methodName = "addReview()";
        logger.info(methodName + "called");

        ReviewDto addReview = this.reviewService.addReview(reviewDto);
        return new ResponseEntity<>(addReview, HttpStatus.CREATED);
    }

    @PutMapping("/updateReview/{customerEmail}")
    public ResponseEntity<ReviewDto> updateReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable("customerEmail") String customerEmail){

        String methodName = "updateReview()";
        logger.info(methodName + "called");

        ReviewDto updateReview=  this.reviewService.updateReview(reviewDto, customerEmail);
        return new ResponseEntity<>(updateReview, HttpStatus.OK);
    }

    @DeleteMapping("/deleteReview/{customerEmail}")
    public ResponseEntity<String> deleteReview(@PathVariable("customerEmail") String customerEmail){

        String methodName = "deleteReview()";
        logger.info(methodName + "called");

        String deleteReview = this.reviewService.deleteReview(customerEmail);
        return new ResponseEntity<>(deleteReview, HttpStatus.OK);
    }
}
