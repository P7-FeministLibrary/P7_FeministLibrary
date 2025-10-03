package com.feministlibrary;

//import com.feministlibrary.model.book.Book;
import com.feministlibrary.model.book.BookDAOImplementation;
import com.feministlibrary.model.book.BookDAOInterface;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        BookDAOInterface dao = new BookDAOImplementation();
        dao.getAll().forEach(System.out::println);

        // añadir libro
        
        /* Book book = new Book("Feminism 3", "how to be a feminist", "234567832");
         dao.insert(book); */
         

        /*
         * borrar libro 
         
         int idToDelete = 9;
         dao.delete(9);

         System.out.println("Libro con ID " + idToDelete + " borrado de la base de datos."); */
        

    }
}
