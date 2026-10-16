package com.example.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;


@Entity
@Table(name = "customer_addresses")
public class CustomerAddress {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "customer_id", nullable = false)
    private Customer customer;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    private boolean isDefault;

    // Getters
    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}