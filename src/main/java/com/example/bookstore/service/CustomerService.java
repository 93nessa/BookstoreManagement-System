package com.example.bookstore.service;

import com.example.bookstore.model.Customer;
import com.example.bookstore.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.bookstore.DTO.CustomerRegistrationRequest;
import com.example.bookstore.model.AccountStatus;




@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
// Register customer
    public Customer registerCustomer(CustomerRegistrationRequest request) {
        // Check if email already exists
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

// Create a new Customer entity
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());


        // Hash the password before saving
        customer.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set timestamps
        customer.setCreatedAt(java.time.LocalDateTime.now());
        customer.setUpdatedAt(java.time.LocalDateTime.now());

        // Set default status
        customer.setStatus(AccountStatus.ACTIVE);

        // Default role assignment
        customer.setRole("CUSTOMER");

        // Save the customer to the database
        return customerRepository.save(customer);
    }

// CRUD operations
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
//Update existing customer
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = customerRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhoneNumber(updatedCustomer.getPhoneNumber());
        existing.setUpdatedAt(java.time.LocalDateTime.now());

        return customerRepository.save(existing);
    }
//update email
    public Customer updateEmail(Long id, String newEmail) {
        Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        if(customerRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("Email already in use");
        }
        
        customer.setEmail(newEmail);
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }
//delete customer
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

//update password
    public Customer updatePassword(Long id, String newPassword) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        String hashed = passwordEncoder.encode(newPassword);
        customer.setPassword(hashed);
        
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

// find by email
    public Customer findByEmail(String email) {
         return customerRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Email not found"));
    }

// Log in
       public Customer login(String email, String password) {
    Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

    if (!passwordEncoder.matches(password, customer.getPassword())) {
        throw new RuntimeException("Invalid email or password");
    }

    return customer;
}
 
}