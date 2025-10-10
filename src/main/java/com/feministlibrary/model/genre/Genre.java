package com.feministlibrary.model.genre;

public class Genre {
    private int idGenre;
    private String genre;

    public Genre() {}
    public Genre(String genre) { this.genre = genre; }

    public int getIdGenre() { return idGenre; }
    public void setIdGenre(int idGenre) { this.idGenre = idGenre; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public String toString() {
        return genre;
    }
}