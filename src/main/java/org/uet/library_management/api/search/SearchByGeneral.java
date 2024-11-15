package org.uet.library_management.api.search;
import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.BooksApiService;
import org.uet.library_management.api.image.ImageURLContext;
import org.uet.library_management.api.image.NormalThumbnail;
import org.uet.library_management.api.image.SmallThumbnail;
import com.google.api.services.books.v1.model.Volume.VolumeInfo.IndustryIdentifiers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.uet.library_management.api.BooksApiService.getApiKey;

/**
 * A class that implements the SearchStrategy interface for conducting general searches
 * using the Google Books API. This class utilizes a singleton BooksApiService to perform
 * the search and returns a list of Book objects based on the given search term.
 */
public class SearchByGeneral implements SearchStrategy{
    private BooksApiService booksApiService = BooksApiService.getInstance();

    /**
     * Performs a search using the Google Books API based on the specified search term.
     *
     * @param searchTerm The term to search for in the Google Books database.
     * @return A list of Book objects that match the search criteria, or an empty list if no matches are found or an error occurs.
     */
    public List<Book> search(String searchTerm) {
        try {
            Books books = booksApiService.createQuery();
            Books.Volumes.List volumesList = books.volumes().list(searchTerm).setKey(getApiKey());
            volumesList.setMaxResults(20L);

            Volumes volumes = volumesList.execute();

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