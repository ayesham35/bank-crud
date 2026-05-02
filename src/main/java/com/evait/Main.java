package com.evait.bank;

import com.evait.bank.dao.impl.CustomerDAOImpl;
import com.evait.bank.dao.impl.AccountDAOImpl;
import com.evait.bank.dao.impl.TransactionDAOImpl;
import com.evait.bank.service.CustomerService;
import com.evait.bank.service.AccountService;
import com.evait.bank.service.TransactionService;
import com.evait.bank.ui.CustomerMenu;
import com.evait.bank.ui.AccountMenu;
import com.evait.bank.ui.TransactionMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CustomerService customerService = new CustomerService(new CustomerDAOImpl());
        AccountService accountService = new AccountService(new AccountDAOImpl());
        TransactionService transactionService = new TransactionService(new TransactionDAOImpl());

        CustomerMenu customerMenu = new CustomerMenu(customerService, scanner);
        AccountMenu accountMenu = new AccountMenu(accountService, scanner);
        TransactionMenu transactionMenu = new TransactionMenu(transactionService, scanner);

        boolean running = true;
        while (running) {
            System.out.println("\n=== Bank Management System ===");
            System.out.println("1. Customers");
            System.out.println("2. Accounts");
            System.out.println("3. Transactions");
            System.out.println("0. Exit");

            System.out.print("Choose: ");
            String input = scanner.nextLine().trim();

            try {
                switch (Integer.parseInt(input)) {
                    case 1 -> customerMenu.show();
                    case 2 -> accountMenu.show();
                    case 3 -> transactionMenu.show();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}
