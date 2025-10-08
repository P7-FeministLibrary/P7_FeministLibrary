package com.feministlibrary.model.author;

import java.util.List;

public interface AuthorDAOInterface {

    void insert(Author author);             
    void update(Author author);            
    void delete(int idAuthor);              
    Author getById(int idAuthor);          

    List<Author> getAll();                 
    List<Author> searchByName(String name); 
    List<Author> searchByLastName(String lastName); 

    Author getByName(String firstName, String lastName); 
}
