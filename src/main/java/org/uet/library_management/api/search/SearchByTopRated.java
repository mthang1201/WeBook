package org.uet.library_management.api.search;

import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import javafx.application.Platform;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.BooksApiService;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.AlertUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.uet.library_management.api.BooksApiService.getApiKey;

/**
 * Implements the SearchStrategy interface to search for top-rated books in a specified category.
 * Filters books based on a minimum average rating of 4.0 and ensures no duplicate ISBN entries.
 */
public class SearchByTopRated implements SearchStrategy {

    /**
     * Singleton instance of BooksApiService to interact with the Google Books API.
     */
    private final BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Service for interacting with the local book database.
     */
    private final BookService bookService = new BookService();

    /**
     * Searches for top-rated books within a specified category and filters out duplicates based on ISBN-13.
     *
     * @param searchTerm The category term used for searching books.
     * @return A list of top-rated books in the specified category, or an empty list if no matches are found.
     */
    @Override
    public List<Book> search(String searchTerm) {
        try {
            Books books = booksApiService.createQuery();
            String query = "category:\"" + searchTerm + "\"";

            Books.Volumes.List volumesList = books.volumes()
                    .list(query)
                    .setKey(getApiKey())
                    .setMaxResults(40L);

            Volumes volumes;
            try {
                volumes = volumesList.execute();
            } catch (IOException e) {
                Platform.runLater(() -> AlertUtil.showErrorAlert(
                        "Không thể kết nối mạng!",
                        "Vui lòng kiểm tra lại kết nối internet!",
                        null,
                        null
                ));
                return Collections.emptyList();
            }

            Set<String> existingIsbns = new HashSet<>();
            for (Book book : bookService.findAll()) {
                existingIsbns.add(book.getIsbn13());
            }

            if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
                List<Book> bookList = new ArrayList<>();
                for (Volume volume : volumes.getItems()) {
                    Book newBook = BookDetailsExtractor.extractBookDetails(volume);
                    if (newBook.getAverageRating() != null
                            && newBook.getAverageRating() >= 4.0
                            && !existingIsbns.contains(newBook.getIsbn13())) {
                        bookList.add(newBook);
                    }
                }
                return bookList;
            }
            return Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
