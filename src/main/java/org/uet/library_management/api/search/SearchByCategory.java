
package org.uet.library_management.api.search;

import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.BooksApiService;
import org.uet.library_management.api.image.ImageURLContext;
import org.uet.library_management.api.image.NormalThumbnail;
import org.uet.library_management.api.image.SmallThumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.uet.library_management.api.BooksApiService.getApiKey;

/**
 * The SearchByCategory class implements the SearchStrategy interface to allow
 * searching for books by their category using the Google Books API.
 * It leverages the BooksApiService singleton to create and execute the API queries.
 */
public class SearchByCategory implements SearchStrategy {
    private BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Searches for books based on the given category using the Google Books API and returns a list of matching books.
     *
     * @param category the category to search for books.
     * @return a list of books matching the given category. If no books are found or an error occurs, returns an empty list.
     */
    public List<Book> search(String category) {
        try {
            Books books = booksApiService.createQuery();
            String query = "subject:\"" + category + "\"";
            //String query = "author:\"" + authorName + "\"";
            Books.Volumes.List volumesList = books.volumes().list(query).setKey(getApiKey());
            volumesList.setMaxResults(15L);
            Volumes volumes = volumesList.execute();

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
