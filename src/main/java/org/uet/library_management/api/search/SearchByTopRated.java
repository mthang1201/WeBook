package org.uet.library_management.api.search;

import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.BooksApiService;
import org.uet.library_management.core.services.documents.BookService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.uet.library_management.api.BooksApiService.getApiKey;

/**
 * A class implementing the SearchStrategy interface to search for books by the top-rated category.
 * This class provides functionality to search for books within a specified category, filtered by a
 * minimum average rating of 4.0 or higher and based on non-duplicate ISBN numbers.
 * It interacts with both an external Books API and a local book database.
 */
public class SearchByTopRated implements SearchStrategy {
    private BooksApiService booksApiService = BooksApiService.getInstance();
    private BookService bookService = new BookService(); // Assuming you have a BookService to interact with the database

    /**
     * Searches for top-rated books within a specified category and filters out books that have already
     * been added to the local collection, ensuring non-duplicate entries based on ISBN-13.
     *
     * @param searchTerm The category term used for searching books.
     * @return A list of top-rated books in the specified category, filtered by a minimum average rating of 4.0,
     *         and not already present in the local collection. Returns an empty list if no such books are found
     *         or if an error occurs during the search process.
     */
    public List<Book> search(String searchTerm) {
        try {
            Books books = booksApiService.createQuery();
            String query = "category:\"" + searchTerm + "\"";
            Books.Volumes.List volumesList = books.volumes().list(query).setKey(getApiKey());
            volumesList.setMaxResults(40L);

            Volumes volumes = volumesList.execute();

            Set<String> existingIsbns = new HashSet<>();
            List<Book> existingBooks = bookService.findAll();
            for (Book book : existingBooks) {
                existingIsbns.add(book.getIsbn13());
            }

            if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
                List<Book> bookList = new ArrayList<>();
                for (Volume volume : volumes.getItems()) {
                    Book newBook = BookDetailsExtractor.extractBookDetails(volume);

                    if (newBook.getAverageRating() != null && newBook.getAverageRating() >= 4.0
                            && !existingIsbns.contains(newBook.getIsbn13())) {
                        bookList.add(newBook);
                    }
                }
                return bookList;
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
