package com.stackroute.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "receipt_id", nullable = false)
    Long receiptId;
    @Column(name = "payment_id")
    String paymentId;
    @Column(name = "order_id")
    String orderId;
//    @Column(name = "customer_contact")
//    String contact;
//    @Column(name = "customer_email")
//    String email;
    @Column(name = "amount")
    Integer amount;
    @Column(name = "currency")
    String currency;
    @Column(name = "payment_method")
    String method;
    @Column(name = "payment_status")
    String paymentStatus;
    @Column(name = "payment_captured")
    String captured;


}

