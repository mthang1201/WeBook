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
 * The SearchByCategory class implements the SearchStrategy interface to allow
 * searching for books by their category using the Google Books API.
 */
public class SearchByCategory implements SearchStrategy {

    /**
     * An instance of BooksApiService used to interact with the Books API.
     * It provides functionality for creating queries to search for books.
     */
    private final BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Searches for books based on the given category using the Google Books API.
     *
     * @param category the category to search for books.
     * @return a list of books matching the given category. If no books are found or an error occurs, returns an empty list.
     */
    @Override
    public List<Book> search(String category) {
        try {
            Books books = booksApiService.createQuery();
            String query = "subject:\"" + category + "\"";

            Books.Volumes.List volumesList = books.volumes()
                    .list(query)
                    .setKey(getApiKey())
                    .setMaxResults(15L);

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
                Collections.shuffle(bookList);
                return bookList;
            }
            return Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
