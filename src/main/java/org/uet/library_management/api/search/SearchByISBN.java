package org.uet.library_management.api.search;

import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import javafx.application.Platform;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.BooksApiService;
import org.uet.library_management.tools.AlertUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.uet.library_management.api.BooksApiService.getApiKey;

/**
 * Implements the SearchStrategy interface to search for books using their ISBN.
 * Utilizes the BooksApiService to interact with the Google Books API and fetch book
 * details based on the provided ISBN number.
 */
public class SearchByISBN implements SearchStrategy {

    /**
     * Singleton instance of BooksApiService used to interact with the Google Books API.
     */
    private final BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Searches for books using the provided ISBN and returns a list of matching books.
     * Utilizes the Google Books API to fetch and extract book details based on the given ISBN number.
     *
     * @param ISBN the International Standard Book Number (ISBN) to search for.
     * @return a list of books that match the given ISBN. If no books are found, returns an empty list.
     */
    @Override
    public List<Book> search(String ISBN) {
        try {
            Books books = booksApiService.createQuery();
            String query = "isbn:" + ISBN;
            Books.Volumes.List volumesList = books.volumes()
                    .list(query)
                    .setKey(getApiKey())
                    .setMaxResults(1L);

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

            if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
                List<Book> bookList = new ArrayList<>();
                for (Volume volume : volumes.getItems()) {
                    Book newBook = BookDetailsExtractor.extractBookDetails(volume);
                    bookList.add(newBook);
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
