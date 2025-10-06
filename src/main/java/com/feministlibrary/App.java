package com.feministlibrary;

import com.feministlibrary.config.DBManager;
import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOImplementation;
import com.feministlibrary.model.author.AuthorDAOInterface;
import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOImplementation;
import com.feministlibrary.model.genre.GenreDAOInterface;
import com.feministlibrary.model.book.Book;
import com.feministlibrary.model.book.BookDAOImplementation;
import com.feministlibrary.model.book.BookDAOInterface;

/**
 * Hello world!
 *
 */
public class App {

    
    public static void main(String[] args) {
        BookDAOInterface dao = new BookDAOImplementation();
        //dao.getAll().forEach(System.out::println);

        AuthorDAOInterface authorDao = new AuthorDAOImplementation();
        //authorDao.getAll().forEach(System.out::println);           
        
        GenreDAOInterface genreDao = new GenreDAOImplementation();
        // añadir libro
        
        Book book = new Book("We should all be feminists", "A book that questions long-held beliefs and gender stereotypes that perpetuate inequality between men and women.", "234567832");
        dao.insert(book);
        authorDao.insert(new Author("Chamamanda", "Ngozi Adichie"));
        genreDao.insert(new Genre());

                 

        /*
         * borrar libro 
         
         int idToDelete = 9;
         dao.delete(9);

         System.out.println("Libro con ID " + idToDelete + " borrado de la base de datos."); */
        

    }
}
