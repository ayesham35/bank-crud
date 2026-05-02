package com.evait.bank.dao;

import com.evait.bank.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountDAO {

    void save(Account account);
    Optional<Account> findById(int id);
    List<Account> findAll();
    void update(Account account);
    void deleteById(int id);
}
