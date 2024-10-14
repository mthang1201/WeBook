package org.uet.library_management.services.api.search;

import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.services.api.BooksApiService;
import org.uet.library_management.services.api.image.ImageURLContext;
import org.uet.library_management.services.api.image.NormalThumbnail;
import org.uet.library_management.services.api.image.SmallThumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.uet.library_management.services.api.BooksApiService.getApiKey;

public class SearchByTitle implements SearchStrategy{
    private BooksApiService booksApiService = BooksApiService.getInstance();

    public List<Book> search(String title) {
        try {
            Books books = booksApiService.createQuery(); // Create a query using the BooksApiService
            String query = "intitle:" + title ;
            //String query = "intitle:\"" + title + "\"";
            Books.Volumes.List volumesList = books.volumes().list(query).setKey(getApiKey());
            volumesList.setMaxResults(10L);

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