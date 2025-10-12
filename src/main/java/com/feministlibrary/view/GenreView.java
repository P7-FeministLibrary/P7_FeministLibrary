package com.feministlibrary.view;

import java.util.Scanner;

import com.feministlibrary.Style;

public class GenreView {

    private final Scanner scanner = new Scanner(System.in);

    public String getGenreName() {
        System.out.print(Style.styleBlue("Enter genre name: "));
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}