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
 * A class implementing the SearchStrategy interface to search for books by their title.
 * It uses the Google Books API to perform the search and retrieve the book details.
 */
public class SearchByTitle implements SearchStrategy{
    private BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Searches for books by their title using the Google Books API.
     *
     * @param title The title of the book to search for.
     * @return A list of books matching the search criteria or an empty list if no matches are found.
     */
    public List<Book> search(String title) {
        try {
            Books books = booksApiService.createQuery();
            String query = "intitle:" + title ;
//            String query = "intitle:\"" + title + "\"";
            Books.Volumes.List volumesList = books.volumes().list(query).setKey(getApiKey());
            volumesList.setMaxResults(10L);
            Volumes volumes;
            try {
                volumes = volumesList.execute();
            } catch (IOException e) {
                Platform.runLater(() -> {
                    AlertUtil.showErrorAlert(
                            "Không thể kết nối mạng!",
                            "Vui lòng kiểm tra lại kết nối internet!",
                            null,
                            null
                    );
                });
                return Collections.emptyList();
            }

            if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
                List<Book> bookList = new ArrayList<>();
                for (Volume volume : volumes.getItems()) {
                    Book newBook = BookDetailsExtractor.extractBookDetails(volume);
                    bookList.add(newBook);
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