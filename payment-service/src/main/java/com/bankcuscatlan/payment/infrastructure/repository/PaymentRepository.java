package com.bankcuscatlan.payment.infrastructure.repository;

import com.bankcuscatlan.payment.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByOrderId(Long id);
}
