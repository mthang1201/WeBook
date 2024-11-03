package org.uet.library_management.api.search;

import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.BooksApiService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.uet.library_management.api.BooksApiService.getApiKey;

public class SearchByTopRated implements SearchStrategy {
    private BooksApiService booksApiService = BooksApiService.getInstance();

    public List<Book> search(String searchTerm) {
        try {
            Books books = booksApiService.createQuery();
            String query = "subject:\"" + searchTerm + "\"";
            Books.Volumes.List volumesList = books.volumes().list(query).setKey(getApiKey());
            volumesList.setMaxResults(40L);

            Volumes volumes = volumesList.execute();

            if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
                List<Book> bookList = new ArrayList<>();
                for (Volume volume : volumes.getItems()) {
                    Book newBook = BookDetailsExtractor.extractBookDetails(volume);
                    if (newBook.getAverageRating() != null && newBook.getAverageRating() >= 4.0) {
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
