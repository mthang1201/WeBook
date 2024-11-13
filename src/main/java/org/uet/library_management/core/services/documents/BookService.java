package org.uet.library_management.core.services.documents;

import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.repositories.documents.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling operations related to Books.
 * This class extends from DocumentService with the specific type of Book.
 */
public class BookService extends DocumentService<Book> {
    public BookService() {
        super();
    }

    /**
     * Retrieves the updated average rating for a book identified by its ISBN-13.
     *
     * @param isbn13 The ISBN-13 identifier of the book.
     * @return The updated average rating of the book.
     */
    public double getUpdatedAverageRating(String isbn13) {
        BookRepository bookRepository = (BookRepository) repository;
        return bookRepository.getUpdatedAverageRating(isbn13);
    }

    public List<Book> getRandomTitlesFromBookmarks() {
        BookRepository bookRepository = (BookRepository) repository;
        return bookRepository.getRandomTitlesFromBookmarks();
    }

    /**
     * Retrieves a list of top-rated books based on a minimum rating and a search term.
     *
     * @param minRating The minimum average rating a book must have to be included in the results.
     * @param searchTerm The search term to filter books by title, author, or description.
     * @return A list of books that match the search criteria and have an average rating equal to or greater than the specified minimum rating.
     */
    public List<Book> getTopRatedSearchTermBooks(double minRating, String searchTerm) {
        BookRepository bookRepository = (BookRepository) repository;
        return bookRepository.getTopRatedSearchTermBooks(minRating, searchTerm);
    }

    /**
     * Loads the repository used for managing Book entities.
     * This method overrides the abstract method in the DocumentService class
     * and initializes the repository with a new instance of BookRepository.
     */
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
