package com.feministlibrary.model.author;

public class Author {
    private int idAuthor;
    private String name;
    private String lastName;

    public Author() {}

    public Author(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public Author(int idAuthor, String name, String lastName) {
        this.idAuthor = idAuthor;
        this.name = name;
        this.lastName = lastName;
    }

    public int getIdAuthor() { return idAuthor; }
    public void setIdAuthor(int idAuthor) { this.idAuthor = idAuthor; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String toString() {
        return "[" + idAuthor + "] " + name + " " + lastName;
    }
}
