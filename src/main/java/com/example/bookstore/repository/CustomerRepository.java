package com.example.bookstore.repository; 

import com.example.bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;




public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional <Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    Customer findByFirstName(String firstName);
    Customer findByLastName(String lastName);
}
