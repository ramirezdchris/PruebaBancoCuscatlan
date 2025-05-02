package com.bankcuscatlan.customers.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "CUSTOMER")
@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "FIRTS_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "AGE")
    private Integer age;
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @Column(name = "USER_CREATED")
    private String userCreated;
    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;
    @Column(name = "USER_UPDATED")
    private String userUpdated;
    @Column(name = "DATE_UPDATED")
    private LocalDateTime dateUpdated;
}
