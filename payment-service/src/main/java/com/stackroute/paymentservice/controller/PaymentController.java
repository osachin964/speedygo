package com.stackroute.paymentservice.controller;
import com.razorpay.RazorpayException;
import com.stackroute.paymentservice.dto.Customer;
import com.stackroute.paymentservice.dto.Payment;
import com.stackroute.paymentservice.entity.Receipt;
import com.stackroute.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;


@SuppressWarnings("PMD")
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/paymentService")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/")
    public String getHome() {
        return "redirect:/home";
    }

    @RequestMapping(value = "/home")
    public String getHomeInit() {
        return "Page";
    }

    @PostMapping(value = "/createPayment")
    @ResponseBody
    public ResponseEntity<?> createOrder(@RequestBody Customer customer) throws RazorpayException {
        try {
            return paymentService.createOrder(customer);
        } catch (RazorpayException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "/invoice")
    @ResponseBody
    public ResponseEntity<?> createInvoice(@RequestBody Customer customer) throws RazorpayException {
        return paymentService.createInvoice(customer);
    }


    @PostMapping ("/complete-payment")
    @ResponseBody
        public ResponseEntity<?> completePayment(@RequestBody Payment request) throws RazorpayException, SignatureException {
        String paymentId = request.getPaymentId();
        String orderId = request.getOrderId();
        String signature = request.getSignature();
        paymentService.validateSignature(paymentId,orderId,signature);
        Receipt receipt=new Receipt();
        receipt = paymentService.createReciept(paymentId);
        return ResponseEntity.ok(receipt);
        }



    }
