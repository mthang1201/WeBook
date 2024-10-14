package org.uet.library_management.services.documents;

import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.repositories.documents.BookRepository;

public class BookService extends DocumentService<Book> {
    public BookService() {
        super();
    }

    @Override
    protected void loadRepository() {
        repository = new BookRepository();
    }
}
