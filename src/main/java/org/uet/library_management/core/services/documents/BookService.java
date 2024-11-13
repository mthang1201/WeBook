package org.uet.library_management.core.services.documents;

import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.repositories.documents.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BookService extends DocumentService<Book> {
    public BookService() {
        super();
    }

    public double getUpdatedAverageRating(String isbn13) {
        BookRepository bookRepository = (BookRepository) repository;
        return bookRepository.getUpdatedAverageRating(isbn13);
    }

    public List<Book> getRandomTitlesFromBookmarks() {
        BookRepository bookRepository = (BookRepository) repository;
        return bookRepository.getRandomTitlesFromBookmarks();
    }

    public List<Book> getTopRatedSearchTermBooks(double minRating, String searchTerm) {
        BookRepository bookRepository = (BookRepository) repository;
        return bookRepository.getTopRatedSearchTermBooks(minRating, searchTerm);
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
