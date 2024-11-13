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

public class SearchByNewest implements SearchStrategy{
    private BooksApiService booksApiService = BooksApiService.getInstance();

    public List<Book> search(String searchTerm) {
        try {
            Books books = booksApiService.createQuery();
            Books.Volumes.List volumesList = books.volumes().list(searchTerm).setKey(getApiKey());
            volumesList.setMaxResults(20L);

            volumesList.setOrderBy("newest");

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