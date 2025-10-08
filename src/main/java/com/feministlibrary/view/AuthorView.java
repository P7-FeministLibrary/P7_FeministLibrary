package com.feministlibrary.view;

import java.util.Scanner;

public class AuthorView {

    private final Scanner scanner = new Scanner(System.in);

    public String getAuthorFullName() {
        System.out.print("Enter author full name (First Last): ");
        return scanner.nextLine().trim();
    }

    public void showAuthors(String[] authors) {
        System.out.println("Authors:");
        for (String author : authors) {
            System.out.println(" - " + author);
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
