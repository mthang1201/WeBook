package org.uet.library_management.services.api;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.Books.Volumes.List;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;
import java.io.IOException;
import java.security.GeneralSecurityException;
public class BooksApiService {
    private static final String APPLICATION_NAME = "Hello";
    private JsonFactory JSON_FACTORY; //tackle json
    private String API_KEY;

    public BooksApiService() {
        JSON_FACTORY = GsonFactory.getDefaultInstance(); //tackle json
//        API_KEY = System.getenv("API_KEY");
        API_KEY = "AIzaSyBcX26qbHjnvGRuzgQ-t8xQOBgQrnZv_zQ";
    }

    public Books createQuery() {
        try {
            return new Books.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void searchByISBN(String ISBN) throws IOException {
        Books books = createQuery();
        String query = "isbn:" + ISBN;

        List volumesList = books.volumes().list(query).setKey(API_KEY);
        volumesList.setMaxResults(1L);

        Volumes volumes = volumesList.execute();
        if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
            System.out.println("Search results for ISBN: " + ISBN);
            for (Volume volume : volumes.getItems()) {
                System.out.println("Title: " + volume.getVolumeInfo().getTitle());
                System.out.println("Authors: " + volume.getVolumeInfo().getAuthors());
                System.out.println("Publisher: " + volume.getVolumeInfo().getPublisher());
                System.out.println("Publisher Date: " + volume.getVolumeInfo().getPublishedDate());
                System.out.println("Description: " + volume.getVolumeInfo().getDescription());
                System.out.println("Categories: " + volume.getVolumeInfo().getCategories());
                System.out.println("Page Count: " + volume.getVolumeInfo().getPageCount());
                System.out.println("Average Ratings: " + volume.getVolumeInfo().getAverageRating());
                System.out.println("Print Type: " + volume.getVolumeInfo().getPrintType());
                System.out.println("Language: " + volume.getVolumeInfo().getLanguage());
                System.out.println("ISBN: " + ISBN);
                System.out.println("=================================");
            }
        } else {
            System.out.println("No books found for the ISBN: " + ISBN);
        }
    }
}