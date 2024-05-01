package com.stackroute.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RazorPay {
    private String applicationFee;

    private String razorpayOrderId;

    private String secretKey;

    private String paymentId;

    private String notes;

    private String imageURL;

    private String theme;

    private String  merchantName;

    private String purchaseDescription;

    private String customerName;

    private String customerEmail;

    private String customerContact;

}
