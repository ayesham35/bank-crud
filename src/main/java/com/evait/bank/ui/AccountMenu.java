package com.evait.bank.ui;

import com.evait.bank.model.Account;
import com.evait.bank.service.AccountService;

import java.util.Scanner;

public class AccountMenu {

    private final AccountService accountService;
    private final Scanner scanner;

    public AccountMenu(AccountService accountService, Scanner scanner) {
        this.accountService = accountService;
        this.scanner = scanner;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Account Menu ===");
            System.out.println("1. Add Account");
            System.out.println("2. List All Accounts");
            System.out.println("3. Find Account by ID");
            System.out.println("4. Update Account");
            System.out.println("5. Delete Account");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> addAccount();
                case 2 -> listAccounts();
                case 3 -> findAccount();
                case 4 -> updateAccount();
                case 5 -> deleteAccount();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addAccount() {
        String number = readString("Account Number: ");
        String type = readString("Type (CHECKING/SAVINGS): ");
        double balance = readDouble("Opening Balance");
        int customerId = readInt("Customer ID: ");
        Account a = new Account(number, type, balance, customerId);
        accountService.add(a);
        System.out.println("Done. Saved: " + a);
    }

    private void listAccounts() {
        accountService.listAll().forEach(System.out::println);
    }

    private void findAccount() {
        int id = readInt("Account ID: ");
        System.out.println(accountService.getById(id));
    }

    private void updateAccount() {
        int id = readInt("Account ID to update: ");
        Account a = accountService.getById(id);
        a.setAccountNumber(readString("New account number: "));
        a.setType(readString("New type: "));
        a.setBalance(readDouble("New balance: "));
        accountService.update(a);
        System.out.println("Done. Updated: " + a);
    }

    private void deleteAccount() {
        int id = readInt("Account ID to delete: ");
        accountService.delete(id);
        System.out.println("Done. Deleted account: " + id);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
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
