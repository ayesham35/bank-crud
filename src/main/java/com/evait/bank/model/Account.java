package com.evait.bank.model;

public class Account {

    private int id;
    private String accountNumber;
    private String type;
    private double balance;
    private int customerId;

    public Account() {}

    public Account(String accountNumber, String type, double balance, int customerId) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = balance;
        this.customerId = customerId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    @Override
    public String toString() {
        return String.format("Account[id = %d, number = %s, type = %s, balance = %.2f, customerId = %d]",
                id, accountNumber, type, balance, customerId);
    }
}
