package com.bankcuscatlan.customers.infrastructure.config.documentation;

import com.bankcuscatlan.customers.infrastructure.repository.CustomerRepository;
import com.bankcuscatlan.customers.model.entity.Customer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final CustomerRepository repo;

    @PostConstruct
    public void init() {
        Customer customer = new Customer();
        customer.setFirstName("Christian Denilson");
        customer.setLastName("Ramirez Sarmento");
        customer.setBirthday(LocalDate.now());
        customer.setAge(28);
        customer.setUserCreated("admin");
        customer.setUserUpdated("admin");
        customer.setDateCreated(LocalDateTime.now());
        customer.setDateUpdated(LocalDateTime.now());
        repo.save(customer);
    }
}

