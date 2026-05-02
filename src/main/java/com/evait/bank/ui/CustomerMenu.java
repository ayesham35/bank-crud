package com.evait.bank.ui;

import com.evait.bank.model.Customer;
import com.evait.bank.service.CustomerService;

import java.util.Scanner;

public class CustomerMenu {

    private final CustomerService customerService;
    private final Scanner scanner;

    public CustomerMenu(CustomerService customerService, Scanner scanner) {
        this.customerService = customerService;
        this.scanner = scanner;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Add Customer");
            System.out.println("2. List All Customers");
            System.out.println("3. Find Customer by ID");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> listCustomers();
                case 3 -> findCustomer();
                case 4 -> updateCustomer();
                case 5 -> deleteCustomer();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addCustomer() {
        String name = readString("Name: ");
        String email = readString("Email: ");
        Customer c = new Customer(name, email);
        customerService.add(c);
        System.out.println("Done. Saved: " + c);
    }

    private void listCustomers() {
        customerService.listAll().forEach(System.out::println);
    }

    private void findCustomer() {
        int id = readInt("Customer ID: ");
        System.out.println(customerService.getById(id));
    }

    private void updateCustomer() {
        int id = readInt("Customer ID to update: ");
        Customer c = customerService.getById(id);
        c.setName(readString("New name: "));
        c.setEmail(readString("New email: "));
        customerService.update(c);
        System.out.println("Done. Updated: " + c);
    }

    private void deleteCustomer() {
        int id = readInt("Customer ID to delete: ");
        customerService.delete(id);
        System.out.println("Done. Deleted customer: " + id);
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

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
