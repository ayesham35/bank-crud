package com.evait.bank.ui;

import com.evait.bank.model.Transaction;
import com.evait.bank.service.TransactionService;

import java.time.LocalDate;
import java.util.Scanner;

public class TransactionMenu {
    private final TransactionService transactionService;
    private final Scanner scanner;

    public TransactionMenu(TransactionService transactionService, Scanner scanner) {
        this.transactionService = transactionService;
        this.scanner = scanner;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Transaction Menu ===");
            System.out.println("1. Add Transaction");
            System.out.println("2. List All Transactions");
            System.out.println("3. Find Transaction by ID");
            System.out.println("4. Delete Transaction");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> addTransaction();
                case 2 -> listTransactions();
                case 3 -> findTransaction();
                case 4 -> deleteTransaction();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addTransaction() {
        int accountId = readInt("Account ID: ");
        String type = readString("Type (DEPOSIT/WITHDRAWAL): ");
        double amount = readDouble("Amount: ");
        Transaction t = new Transaction(accountId, type, amount, LocalDate.now());
        transactionService.add(t);
        System.out.println("Done. Saved: " + t);
    }

    private void listTransactions() {
        transactionService.listAll().forEach(System.out::println);
    }

    private void findTransaction() {
        int id = readInt("Transaction ID: ");
        System.out.println(transactionService.getById(id));
    }

    private void deleteTransaction() {
        int id = readInt("Transaction ID to delete: ");
        transactionService.delete(id);
        System.out.println("Done. Deleted transaction " + id);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
