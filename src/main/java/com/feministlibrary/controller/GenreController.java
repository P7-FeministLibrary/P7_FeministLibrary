package com.feministlibrary.controller;

import java.util.List;
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
        if (genres.isEmpty()) {
            view.showMessage("No genres found.");
        } else {
            genres.forEach(g -> view.showMessage(g.toString()));
        }
    }

    public void addGenre() {
        String name = view.getGenreName();
        Genre genre = genreDao.getByName(name);
        if (genre == null) {
            genre = new Genre(name);
            genreDao.insert(genre);
            view.showMessage("Genre added successfully!");
        } else {
            view.showMessage("Genre already exists.");
        }
    }

    public void editGenre() {
        String name = view.getGenreName();
        Genre genre = genreDao.getByName(name);
        if (genre == null) {
            view.showMessage("Genre not found.");
            return;
        }

        String newName = view.getGenreName();
        genre.setGenre(newName);
        genreDao.update(genre);
        view.showMessage("Genre updated successfully!");
    }
}

