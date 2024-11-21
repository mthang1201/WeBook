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
import com.google.api.services.books.v1.model.Volume.VolumeInfo.IndustryIdentifiers;
import org.uet.library_management.tools.AlertUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.uet.library_management.api.BooksApiService.getApiKey;

/**
 * A class that implements the SearchStrategy interface to search for books using the Google Books API,
 * specifically sorting the results to show the newest books first.
 * The search term provided is used to query the Google Books API, limiting the results to 20,
 * and ordering them by their published date in descending order.
 */
public class SearchByNewest implements SearchStrategy{
    private BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Searches for books using the Google Books API with a given search term, limiting the results to 20
     * and ordering them by the newest publication date.
     *
     * @param searchTerm the term used to search for books
     * @return a list of books that match the search term, shuffled randomly;
     *         an empty list if no books are found or in case of an error
     */
    public List<Book> search(String searchTerm) {
        try {
            Books books = booksApiService.createQuery();
            Books.Volumes.List volumesList = books.volumes().list(searchTerm).setKey(getApiKey());
            volumesList.setMaxResults(20L);

            volumesList.setOrderBy("newest");

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
                Collections.shuffle(bookList);
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