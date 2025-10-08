    package com.feministlibrary.controller;

    import com.feministlibrary.model.author.AuthorDAOInterface;
    import com.feministlibrary.model.book.BookDAOInterface;
    import com.feministlibrary.model.genre.GenreDAOInterface;
    import com.feministlibrary.view.BookView;

    public class BookController {

        private final BookDAOInterface bookDao;
        private final AuthorDAOInterface authorDao;
        private final GenreDAOInterface genreDao;
        private final BookView view;

        public BookController(BookDAOInterface bookDao, AuthorDAOInterface authorDao,
                            GenreDAOInterface genreDao, BookView view) {
            this.bookDao = bookDao;
            this.authorDao = authorDao;
            this.genreDao = genreDao;
            this.view = view;
        }

        public void listBooks() {
            bookDao.getAll().forEach(b -> view.showMessage(b.toString()));
        }

        public void addBook() {
           
        }

        public void editBook() {}

        public void deleteBook() {}

        public void searchByTitle() {}

        public void searchByAuthor() {}

        public void searchByGenre() {}
    }
