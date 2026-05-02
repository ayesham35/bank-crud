package com.evait.bank.service;

import com.evait.bank.dao.CustomerDAO;
import com.evait.bank.exception.ValidationException;
import com.evait.bank.model.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService (CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Customer add(Customer customer) {
        validate(customer);
        customerDAO.save(customer);
        return customer;
    }

    public List<Customer> listAll() {
        return customerDAO.findAll();
    }

    public Customer getById(int id) {
        return customerDAO.findById(id)
                .orElseThrow(() -> new ValidationException("No customer found with id " + id));
    }

    public Customer update(Customer customer) {
        validate(customer);
        customerDAO.update(customer);
        return customer;
    }

    public void delete(int id) {
        getById(id);
        customerDAO.deleteById(id);
    }

    private void validate(Customer customer) {
        if (customer.getName() == null || customer.getName().isBlank())
            throw new ValidationException("Name is required");
        if (customer.getEmail() == null || customer.getEmail().isBlank())
            throw new ValidationException("Email is required");
    }
}
