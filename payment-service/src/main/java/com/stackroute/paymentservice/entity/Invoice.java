package com.stackroute.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "description")
    String description;
    @Column(name = "customer_name")
    String customerName;
    @Column(name = "customer_contact")
    String customerContact;
    @Column(name = "amount")
    String amount;
    @Column(name = "status")
    String status;
    @Column(name = "currency")
    String currency;

}
