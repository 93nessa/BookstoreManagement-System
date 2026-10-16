package com.example.bookstore.controller;

import com.example.bookstore.DTO.LoginRequest;
import com.example.bookstore.DTO.CustomerRegistrationRequest;
import com.example.bookstore.DTO.LoginResponse;
import com.example.bookstore.model.Customer;
import com.example.bookstore.service.CustomerService;
import com.example.bookstore.service.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final JwtUtil jwtUtil;
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/register")
    public Customer registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        //process customer registration request
        return customerService.registerCustomer(request);
    }

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    Customer customer = customerService.login(request.getEmail(), request.getPassword());
    String token = jwtUtil.generateToken(customer);

    LoginResponse response = new LoginResponse(
        token,
        customer.getEmail(),
        customer.getFirstName(),
        customer.getLastName()

    );

    return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(id, updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    //
    @PatchMapping("/{id}/email")
    public ResponseEntity<Customer> updateEmail(
        @PathVariable Long id,
        @RequestBody Map<String, String> request
    ){
        String newEmail = request.get("email");
        Customer updated = customerService.updateEmail(id, newEmail);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Customer> updatePassword(
        @PathVariable Long id,
        @RequestBody Map<String, String> request
    ) {
        String newPassword = request.get("password");
        Customer updated = customerService.updatePassword(id, newPassword);
        return ResponseEntity.ok(updated);
    }
}