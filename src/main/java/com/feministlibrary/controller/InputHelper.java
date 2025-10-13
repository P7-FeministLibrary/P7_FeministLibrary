package com.feministlibrary.controller;

import com.feministlibrary.view.BookView;
import com.feministlibrary.view.AuthorView;
import com.feministlibrary.view.GenreView;
import com.feministlibrary.view.BackToMenuException;
import com.feministlibrary.Style;

public class InputHelper {

    private final Object view;

    public InputHelper(Object view) {
        this.view = view;
    }

    public String getInputOrBack(String prompt) throws BackToMenuException {
        String input;
        if (view instanceof BookView bv) {
            input = bv.getInput(prompt);
        } else if (view instanceof AuthorView av) {
            input = av.getAuthorFullName();
        } else if (view instanceof GenreView gv) {
            input = gv.getGenreName();
        } else {
            throw new IllegalArgumentException("Unsupported view type");
        }

        if ("0".equals(input)) throw new BackToMenuException();
        return input;
    }

    public void showCancelMessage() {
        if (view instanceof BookView bv) bv.showMessage(Style.styleYellow("Returning to main menu."));
        else if (view instanceof AuthorView av) av.showMessage(Style.styleYellow("Returning to main menu."));
        else if (view instanceof GenreView gv) gv.showMessage(Style.styleYellow("Returning to main menu."));
    }
}
