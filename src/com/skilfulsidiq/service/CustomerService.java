package com.skilfulsidiq.service;

import com.skilfulsidiq.model.Customer;

import java.util.*;

public class CustomerService {
    private static final CustomerService SINGLETON = new CustomerService();

    private final Map<String, Customer> customers = new HashMap<String, Customer>();

    public static CustomerService getSingleton() {
        return SINGLETON;
    }

    public void addCustomer(String firstName, String lastName, String email){
        Customer newCustomer = new Customer(firstName,lastName,email);
        customers.put(email,newCustomer);
    }

    public Customer getCustomer(String email){
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers(){
    return customers.values();
    }
}
