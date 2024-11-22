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
 * Implements the SearchStrategy interface for conducting general searches
 * using the Google Books API. Returns a list of Book objects based on the given search term.
 */
public class SearchByGeneral implements SearchStrategy {

    /**
     * Singleton instance of BooksApiService used to interact with the Google Books API.
     */
    private final BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Performs a search using the Google Books API based on the specified search term.
     *
     * @param searchTerm the term to search for in the Google Books database.
     * @return a list of Book objects that match the search criteria, or an empty list if no matches are found or an error occurs.
     */
    @Override
    public List<Book> search(String searchTerm) {
        try {
            Books books = booksApiService.createQuery();
            Books.Volumes.List volumesList = books.volumes()
                    .list(searchTerm)
                    .setKey(getApiKey())
                    .setMaxResults(20L);

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
