package com.evait.bank.model;

import java.time.LocalDate;

public class Transaction {

    private int id;
    private int accountId;
    private String type;
    private double amount;
    private LocalDate txnDate;

    public Transaction() {}

    public Transaction(int accountId, String type, double amount, LocalDate txnDate) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.txnDate = txnDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getTxnDate() { return txnDate; }
    public void setTxnDate(LocalDate txnDate) { this.txnDate = txnDate; }

    @Override
    public String toString() {
        return String.format("Transaction[id = %d, accountId = %d, type = %s, amount = %.2f, date = %s]",
                id, accountId, type, amount, txnDate);
    }
}
