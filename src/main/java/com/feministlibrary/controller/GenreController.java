package com.feministlibrary.controller;

import java.util.List;
import com.feministlibrary.Style;
import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOInterface;
import com.feministlibrary.view.GenreView;

public class GenreController {

    private final GenreDAOInterface genreDao;
    private final GenreView view;

    public GenreController(GenreDAOInterface genreDao, GenreView view) {
        this.genreDao = genreDao;
        this.view = view;
    }

    public void listGenres() {
        List<Genre> genres = genreDao.getAll();
        if (genres.isEmpty()) view.showMessage(Style.styleRed("No genres found."));
        else genres.forEach(g -> view.showMessage(g.toString()));
    }

    public void addGenre() {
        String name;
        do {
            name = view.getGenreName().trim();
            if (name.isEmpty()) view.showMessage(Style.styleRed("Genre name cannot be empty."));
        } while (name.isEmpty());

        Genre genre = genreDao.getByName(name);
        if (genre == null) {
            genreDao.insert(new Genre(name));
            view.showMessage(Style.styleGreen("Genre added successfully!"));
        } else {
            view.showMessage(Style.styleRed("Genre already exists."));
        }
    }

    public void editGenre() {
        String name;
        do {
            name = view.getGenreName().trim();
            if (name.isEmpty()) view.showMessage(Style.styleRed("Genre name cannot be empty."));
        } while (name.isEmpty());

        Genre genre = genreDao.getByName(name);
        if (genre == null) {
            view.showMessage(Style.styleRed("Genre not found."));
            return;
        }

        String newName;
        do {
            newName = view.getGenreName().trim();
            if (newName.isEmpty()) view.showMessage(Style.styleRed("New genre name cannot be empty."));
        } while (newName.isEmpty());

        genre.setGenre(newName);
        genreDao.update(genre);
        view.showMessage(Style.styleGreen("Genre updated successfully!"));
    }
}
