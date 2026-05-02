package com.evait.bank.dao.impl;

import com.evait.bank.config.DBUtil;
import com.evait.bank.dao.CustomerDAO;
import com.evait.bank.exception.DataAccessException;
import com.evait.bank.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customers (name, email) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) customer.setId(keys.getInt(1));
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to save customer", e);
        }
    }

    @Override
    public Optional<Customer> findById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to find customer " + id, e);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) customers.add(mapRow(rs));
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to list customers", e);
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setInt(3, customer.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to update customer", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Failed to delete customer", e);
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setEmail(rs.getString("email"));
        return c;
    }
}
