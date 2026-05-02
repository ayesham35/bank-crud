package com.evait.bank.service;

import com.evait.bank.dao.TransactionDAO;
import com.evait.bank.exception.ValidationException;
import com.evait.bank.model.Transaction;

import java.util.List;

public class TransactionService {

    private final TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public Transaction add(Transaction transaction) {
        validate(transaction);
        transactionDAO.save(transaction);
        return transaction;
    }

    public List<Transaction> listAll() {
        return transactionDAO.findAll();
    }

    public Transaction getById(int id) {
        return transactionDAO.findById(id)
                .orElseThrow(() -> new ValidationException("No transaction found with id " + id));
    }

    public void delete(int id) {
        getById(id);
        transactionDAO.deleteById(id);
    }

    private void validate(Transaction transaction) {
        if (transaction.getAccountId() <= 0)
            throw new ValidationException("A valid account must be selected.");
        if (transaction.getType() == null || transaction.getType().isBlank())
            throw new ValidationException("Transaction type is required");
        if (transaction.getAmount() <= 0)
            throw new ValidationException("Amount must be greater than zero");
        if (transaction.getTxnDate() == null)
            throw new ValidationException("Transaction date is required");
    }
}
