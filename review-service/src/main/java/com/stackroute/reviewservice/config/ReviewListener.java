package com.stackroute.reviewservice.config;


import com.stackroute.reviewservice.dto.ReviewConsumerDto;
import com.stackroute.reviewservice.dto.ReviewDto;
import com.stackroute.reviewservice.service.ReviewService;
import com.stackroute.reviewservice.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReviewListener {


    @Autowired
    ReviewService reviewService;

    @RabbitListener(queues = AppConstants.REVIEW_QUEUE)
    public void getBookingDtoFromRabbitMq(ReviewConsumerDto reviewConsumerDto)  {
       if (null != reviewConsumerDto.getCustomerId() && null !=reviewConsumerDto.getTransporterId()) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setCustomerEmail(reviewConsumerDto.getCustomerId());
            reviewDto.setTransporterId(reviewConsumerDto.getTransporterId());
            reviewDto.setBookingId(reviewConsumerDto.getBookingId());
            reviewDto.setCustomerName(reviewConsumerDto.getCustomerName());
            reviewService.addReview(reviewDto);
            log.info("message consumed and stored successfully in DB");
        }
       else {
            log.info("message consumed failed");
        }
    }

}
