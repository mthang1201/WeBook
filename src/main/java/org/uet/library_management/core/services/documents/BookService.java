package org.uet.library_management.core.services.documents;

import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.repositories.documents.BookRepository;

import java.util.Arrays;
import java.util.List;

public class BookService extends DocumentService<Book> {
    public BookService() {
        super();
    }

    @Override
    protected void loadRepository() {
        repository = new BookRepository();
    }

    public List<String> suggest(String searchText) {
        return Arrays.asList(
            "asdfsad",
                "sdfasdf",
                "hello",
                "cbr650rr",
                "africa twin"
        );
    }
}
