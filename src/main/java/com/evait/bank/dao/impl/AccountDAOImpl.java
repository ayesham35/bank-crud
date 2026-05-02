package com.evait.bank.dao.impl;

import com.evait.bank.config.DBUtil;
import com.evait.bank.dao.AccountDAO;
import com.evait.bank.exception.DataAccessException;
import com.evait.bank.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO accounts (account_number, type, balance, customer_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getType());
            ps.setDouble(3, account.getBalance());
            ps.setInt(4, account.getCustomerId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) account.setId(keys.getInt(1));
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to save account", e);
        }
    }

    @Override
    public Optional<Account> findById(int id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to find account " + id, e);
        }
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) accounts.add(mapRow(rs));
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to list accounts", e);
        }
        return accounts;
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE accounts SET account_number = ?, type = ?, balance = ?, customer_id = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getType());
            ps.setDouble(3, account.getBalance());
            ps.setInt(4, account.getCustomerId());
            ps.setInt(5, account.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to update account", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to delete account", e);
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setId(rs.getInt("id"));
        a.setAccountNumber(rs.getString("account_number"));
        a.setType(rs.getString("type"));
        a.setBalance(rs.getDouble("balance"));
        a.setCustomerId(rs.getInt("customer_id"));
        return a;
    }

}
