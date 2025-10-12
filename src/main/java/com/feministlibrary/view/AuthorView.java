package com.feministlibrary.view;

import java.util.Scanner;

import com.feministlibrary.Style;

public class AuthorView {

    private final Scanner scanner = new Scanner(System.in);

    public String getAuthorFullName() {
    System.out.println(Style.styleBlue("Enter author full name (First Last): \n"));
    return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
