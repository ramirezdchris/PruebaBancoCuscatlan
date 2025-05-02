package com.bankcuscatlan.orders.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    private LocalDateTime date = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> items = new ArrayList<>();

    @Column(name = "USER_CREATED")
    public String userCreated;
    @Column(name = "DATE_CREATED")
    public LocalDateTime dateCreated;
    @Column(name = "USER_UPDATED")
    public String userUpdated;
    @Column(name = "DATE_UPDATED")
    public LocalDateTime dateUpdated;
}
