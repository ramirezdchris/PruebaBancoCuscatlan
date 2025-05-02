package com.bankcuscatlan.payment.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAYMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOTAL")
    private double total;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;
}
