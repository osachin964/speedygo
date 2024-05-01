package com.stackroute.reviewservice.repository;

import com.stackroute.reviewservice.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {


    List<Review> findByTransporterId(String transporterId);

    List<Review> findByBookingId(String bookingId);
}
