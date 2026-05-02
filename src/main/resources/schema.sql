CREATE DATABASE IF NOT EXISTS bank_db;
USE bank_db;

CREATE TABLE customers (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE accounts (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          account_number VARCHAR(20) UNIQUE NOT NULL,
                          type VARCHAR(20) NOT NULL,
                          balance DECIMAL(10, 2) DEFAULT 0.00,
                          customer_id INT NOT NULL,
                          FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE transactions (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              account_id INT NOT NULL,
                              type VARCHAR(20) NOT NULL,
                              amount DECIMAL(10, 2) NOT NULL,
                              txn_date DATE NOT NULL,
                              FOREIGN KEY (account_id) REFERENCES accounts(id)
);