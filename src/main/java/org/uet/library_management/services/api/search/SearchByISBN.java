package org.uet.library_management.services.api.search;
import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import org.uet.library_management.services.api.BooksApiService;

import java.io.IOException;

import static org.uet.library_management.services.api.BooksApiService.getApiKey;

public class SearchByISBN implements SearchStrategy{
    private BooksApiService booksApiService = BooksApiService.getInstance();

    public void search(String ISBN) {
        try {
            Books books = booksApiService.createQuery(); // Create a query using the BooksApiService
            String query = "isbn:" + ISBN;

            Books.Volumes.List volumesList = books.volumes().list(query).setKey(getApiKey());
            volumesList.setMaxResults(1L);

            Volumes volumes = volumesList.execute();
            if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
                System.out.println("Search results for ISBN: " + ISBN);
                for (Volume volume : volumes.getItems()) {
                    System.out.println("Title: " + volume.getVolumeInfo().getTitle());
                    System.out.println("Authors: " + volume.getVolumeInfo().getAuthors());
                    System.out.println("Publisher: " + volume.getVolumeInfo().getPublisher());
                    System.out.println("Published Date: " + volume.getVolumeInfo().getPublishedDate());
                    System.out.println("Description: " + volume.getVolumeInfo().getDescription());
                    System.out.println("Categories: " + volume.getVolumeInfo().getCategories());
                    System.out.println("Page Count: " + volume.getVolumeInfo().getPageCount());
                    System.out.println("Average Rating: " + volume.getVolumeInfo().getAverageRating());
                    System.out.println("Print Type: " + volume.getVolumeInfo().getPrintType());
                    System.out.println("Language: " + volume.getVolumeInfo().getLanguage());
                    System.out.println("ISBN: " + ISBN);
                    System.out.println("=================================");
                }
            } else {
                System.out.println("No books found for the ISBN: " + ISBN);
            }
        } catch (IOException e) {
            System.err.println("Error occurred while searching for ISBN: " + ISBN);
            e.printStackTrace();
        }
    }
}