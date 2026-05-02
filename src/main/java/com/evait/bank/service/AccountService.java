package com.evait.bank.service;

import com.evait.bank.dao.AccountDAO;
import com.evait.bank.exception.ValidationException;
import com.evait.bank.model.Account;

import java.util.List;

public class AccountService {

    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account add(Account account) {
        validate(account);
        accountDAO.save(account);
        return account;
    }

    public List<Account> listAll() {
        return accountDAO.findAll();
    }

    public Account getById(int id) {
        return accountDAO.findById(id)
                .orElseThrow(() -> new ValidationException("No account found with id " + id));
    }

    public Account update(Account account) {
        validate(account);
        accountDAO.update(account);
        return account;
    }

    public void delete(int id) {
        getById(id);
        accountDAO.deleteById(id);
    }

    private void validate(Account account) {
        if (account.getAccountNumber() == null || account.getAccountNumber().isBlank())
            throw new ValidationException("Account number is required");
        if (account.getType() == null || account.getType().isBlank())
            throw new ValidationException("Account type is required");
        if (account.getBalance() < 0)
            throw new ValidationException("Balance cannot be negative");
        if (account.getCustomerId() <= 0)
            throw new ValidationException("A valid customer must be selected");
    }
}
