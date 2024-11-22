package org.uet.library_management.api.search;

import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import javafx.application.Platform;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.BooksApiService;
import org.uet.library_management.api.image.ImageURLContext;
import org.uet.library_management.api.image.NormalThumbnail;
import org.uet.library_management.api.image.SmallThumbnail;
import org.uet.library_management.tools.AlertUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.uet.library_management.api.BooksApiService.getApiKey;

/**
 * The SearchByAuthor class implements the SearchStrategy interface and provides
 * functionality to search for books by author name using the Google Books API.
 */
public class SearchByAuthor implements SearchStrategy {

    /**
     * An instance of BooksApiService used to interact with the Books API.
     * It provides functionality for creating queries to search for books based on various criteria.
     */
    private final BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Executes a search query to find books by a specific author using the Google Books API.
     *
     * @param authorName The name of the author whose books are to be searched.
     * @return A list of books by the specified author, or an empty list if no books are found or if an error occurs.
     */
    @Override
    public List<Book> search(String authorName) {
        try {
            Books books = booksApiService.createQuery();
            String query = "author:" + authorName;

            Books.Volumes.List volumesList = books.volumes()
                    .list(query)
                    .setKey(getApiKey())
                    .setMaxResults(10L);

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
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
