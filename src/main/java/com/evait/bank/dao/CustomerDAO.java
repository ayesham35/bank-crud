package com.evait.bank.dao;

import com.evait.bank.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    void save(Customer customer);
    Optional<Customer> findById(int id);
    List<Customer> findAll();
    void update(Customer customer);
    void deleteById(int id);
}
