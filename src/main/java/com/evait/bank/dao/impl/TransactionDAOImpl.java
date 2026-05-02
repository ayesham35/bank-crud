package com.evait.bank.dao.impl;

import com.evait.bank.config.DBUtil;
import com.evait.bank.dao.TransactionDAO;
import com.evait.bank.exception.DataAccessException;
import com.evait.bank.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (account_id, type, amount, txn_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, transaction.getAccountId());
            ps.setString(2, transaction.getType());
            ps.setDouble(3, transaction.getAmount());
            ps.setDate(4, Date.valueOf(transaction.getTxnDate()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) transaction.setId(keys.getInt(1));
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to save transaction", e);
        }
    }

    @Override
    public Optional<Transaction> findById(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to find transaction " + id, e);
        }
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) transactions.add(mapRow(rs));
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to list transactions", e);
        }
        return transactions;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to delete transacgtion", e);
        }
    }

    private Transaction mapRow(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        t.setAccountId(rs.getInt("account_id"));
        t.setType(rs.getString("type"));
        t.setAmount(rs.getDouble("amount"));
        t.setTxnDate(rs.getDate("txn_date").toLocalDate());
        return t;
    }
}
