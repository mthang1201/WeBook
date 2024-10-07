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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.uet.library_management.services.api.BooksApiService.getApiKey;

public class SearchByISBN implements SearchStrategy{
    private BooksApiService booksApiService = BooksApiService.getInstance();

    public List<Book> search(String ISBN) {
        try {
            Books books = booksApiService.createQuery();
            String query = "isbn:" + ISBN;

            Books.Volumes.List volumesList = books.volumes().list(query).setKey(getApiKey());
            volumesList.setMaxResults(1L);

            Volumes volumes = volumesList.execute();

            if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
                List<Book> bookList = new ArrayList<>();
                List<String> isbnList = new ArrayList<>();
                Volume volume = volumes.getItems().getFirst();
                if (volume.getVolumeInfo().getIndustryIdentifiers() != null) {
                    isbnList = volume.getVolumeInfo().getIndustryIdentifiers().stream()
                            .filter(identifier -> identifier.getType().equals("ISBN_10") || identifier.getType().equals("ISBN_13"))
                            .map(identifier -> identifier.getIdentifier())
                            .collect(Collectors.toList());
                }
                ImageURLContext imageURLContext = new ImageURLContext();
                imageURLContext.setImageURLGenerator(new NormalThumbnail(volume));
                if (imageURLContext.getImageURL() == null) {
                    imageURLContext.setImageURLGenerator(new SmallThumbnail(volume));
                }
                Book newBook = new Book(
                        volume.getVolumeInfo().getTitle(),
                        volume.getVolumeInfo().getAuthors(),
                        volume.getVolumeInfo().getPublisher(),
                        volume.getVolumeInfo().getPublishedDate(),
                        volume.getVolumeInfo().getDescription(),
                        volume.getVolumeInfo().getCategories(),
                        volume.getVolumeInfo().getPageCount(),
                        volume.getVolumeInfo().getAverageRating(),
                        volume.getVolumeInfo().getMaturityRating(),
                        volume.getVolumeInfo().getPrintType(),
                        volume.getVolumeInfo().getLanguage(),
                        isbnList,
                        imageURLContext.getImageURL());
                bookList.add(newBook);
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