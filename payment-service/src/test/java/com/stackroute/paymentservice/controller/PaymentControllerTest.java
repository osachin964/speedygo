package com.stackroute.paymentservice.controller;

import static com.stackroute.paymentservice.TestUtils.asJsonString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.razorpay.RazorpayException;
import com.stackroute.paymentservice.dto.Customer;
import com.stackroute.paymentservice.dto.Payment;
import com.stackroute.paymentservice.entity.Receipt;
import com.stackroute.paymentservice.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.SignatureException;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void createOrderTest() throws Exception {
        Customer customer = new Customer();

        when(paymentService.createOrder(customer))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(post("/paymentService/createPayment")
                        .contentType("application/json")
                        .content(asJsonString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    public void createInvoiceTest() throws Exception {
        Customer customer = new Customer();

        when(paymentService.createInvoice(customer))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(post("/paymentService/invoice")
                        .contentType("application/json")
                        .content(asJsonString(customer)))
                .andExpect(status().isOk());
    }
    @Test
    public void completePaymentTest() throws RazorpayException, SignatureException {
        Payment payment=new Payment();
        Receipt receipt=new Receipt();
        payment.setOrderId("orderId");
        payment.setPaymentId("paymentId");
        payment.setSignature("signature");
        when(paymentService.createReciept("paymentId")).thenReturn(receipt);
        assertEquals(new ResponseEntity<>(receipt, HttpStatus.OK), paymentController.completePayment(payment));
        verify(paymentService, times(1)).validateSignature("paymentId", "orderId", "signature");
        verify(paymentService, times(1)).createReciept("paymentId");
    }
}
