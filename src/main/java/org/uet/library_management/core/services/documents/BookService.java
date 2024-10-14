package org.uet.library_management.core.services.documents;

import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.repositories.documents.BookRepository;

public class BookService extends DocumentService<Book> {
    public BookService() {
        super();
    }

    @Override
    protected void loadRepository() {
        repository = new BookRepository();
    }
}
