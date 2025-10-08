package com.feministlibrary.view;

import java.util.Scanner;

public class GenreView {

    private final Scanner scanner = new Scanner(System.in);

    public String getGenreName() {
        System.out.print("Enter genre name: ");
        return scanner.nextLine().trim();
    }

    public void showGenres(String[] genres) {
        System.out.println("Genres:");
        for (String genre : genres) {
            System.out.println(" - " + genre);
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
