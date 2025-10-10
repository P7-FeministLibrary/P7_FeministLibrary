package com.feministlibrary.view;

import java.util.Scanner;

public class AuthorView {

    private final Scanner scanner = new Scanner(System.in);

    public String getAuthorFullName() {
        System.out.print("Enter author full name (First Last): ");
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
