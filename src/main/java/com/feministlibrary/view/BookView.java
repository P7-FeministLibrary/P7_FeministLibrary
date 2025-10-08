package com.feministlibrary.view;

import java.util.Scanner;

public class BookView {

    private final Scanner scanner = new Scanner(System.in);

    public String showMenu() {
        System.out.println("Welcome to The Matilda Library.");
        System.out.println("1. List book catalog");
        System.out.println("2. Add new book");
        System.out.println("3. Edit book");
        System.out.println("4. Delete book");
        System.out.println("5. Search by title");
        System.out.println("6. Search by author");
        System.out.println("7. Search by genre");
        System.out.println("0. Exit");
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }
}
