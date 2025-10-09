package com.feministlibrary.view;

import java.util.Scanner;
import com.feministlibrary.Style;

public class BookView {

    private final Scanner scanner = new Scanner(System.in);

    public String showMenu() {
        System.out.println(Style.titleStyle("\n     Welcome to The Matilda Library.     \n"));
        System.out.println(Style.styleOption("1. List book catalog"));
        System.out.println(Style.styleOption("2. Add new book"));
        System.out.println(Style.styleOption("3. Edit book"));
        System.out.println(Style.styleOption("4. Delete book"));
        System.out.println(Style.styleOption("5. Search by title"));
        System.out.println(Style.styleOption("6. Search by author"));
        System.out.println(Style.styleOption("7. Search by genre"));
        System.out.println(Style.styleOption("0. Exit"));
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
