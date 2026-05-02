package com.evait.bank.dao;

import com.evait.bank.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionDAO {

    void save(Transaction transaction);
    Optional<Transaction> findById(int id);
    List<Transaction> findAll();
    void deleteById(int id);
}
