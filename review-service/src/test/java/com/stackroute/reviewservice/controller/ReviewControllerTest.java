package com.stackroute.reviewservice.controller;

import com.stackroute.reviewservice.dto.ReviewDto;
import com.stackroute.reviewservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService mockReviewService;

    @Test
    void testGetReviewByCompany() throws Exception {
        // Setup
        // Configure ReviewService.getReviewByCompany(...).
        final List<ReviewDto> reviewDtos = List.of(new ReviewDto("SakshiSharma@gmail.com","Sakshi Sharma","BlueDart@gmail.com","BK600", 6, "Service was good"));
        when(mockReviewService.getReviewByTransporter("BlueDart@gmail.com")).thenReturn(reviewDtos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/reviewService/getReview/{transporterId}", "BlueDart@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"customerEmail\":\"SakshiSharma@gmail.com\",\"customerName\":\"Sakshi Sharma\",\"transporterId\":\"BlueDart@gmail.com\",\"bookingId\":\"BK600\",\"rating\":6.0,\"remark\":\"Service was good\"}]");
    }

    @Test
    void testGetReviewByCompany_ReviewServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockReviewService.getReviewByTransporter("SakshiSharma@gmail.com")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/reviewService/getReview/{transporterId}", "SakshiSharma@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetAverageRatingOfCompany() throws Exception {
        // Setup
        when(mockReviewService.getAverageRatingOfTransporter("SakshiSharma@gmail.com")).thenReturn(7.0);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/reviewService/getRating/{transporterId}", "SakshiSharma@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("7.0");
    }



    @Test
    void testDeleteReview() throws Exception {
        // Setup
        when(mockReviewService.deleteReview("SakshiSharma@gmail.com")).thenReturn("Deleted");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        delete("/reviewService/deleteReview/{customerEmail}", "SakshiSharma@gmail.com")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Deleted");
    }



}