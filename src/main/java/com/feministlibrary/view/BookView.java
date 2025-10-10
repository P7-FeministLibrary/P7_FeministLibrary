package com.feministlibrary.view;

import java.util.Scanner;
import com.feministlibrary.Style;

public class BookView {

    private final Scanner scanner = new Scanner(System.in);

    public String showMenu() {
    System.out.println("\n\n" + Style.titleStyle("  Welcome to The Matilda Library  ") + "\n");
        String[] options = {
            "1. List book catalog",
            "2. Add new book",
            "3. Edit book",
            "4. Delete book",
            "5. Search by title",
            "6. Search by author",
            "7. Search by genre",
            "0. Exit"
        };
        for (String option : options) {
            System.out.println(Style.styleOption(option));
        }
        System.out.println("\n\n" + Style.styleGreen("Please, choose an option (1 to 7) (0 to exit): ")+ "\n");
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getInput(String prompt) throws BackToMenuException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.equals("0")) throw new BackToMenuException();
        return input;
     }

    public void close() {
        scanner.close();
    }
}
