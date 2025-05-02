package com.bankcuscatlan.orders.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "ORDERS_DETAIL")
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_PRICE")
    private double productPrice;
    @Column(name = "QUANTITY")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
