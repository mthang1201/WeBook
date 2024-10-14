package org.uet.library_management.api;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.books.v1.Books;

import java.io.IOException;
import java.security.GeneralSecurityException;
public class BooksApiService {
    private static final String APPLICATION_NAME = "Hello";
    private JsonFactory JSON_FACTORY; //tackle json
    private static final String API_KEY = "AIzaSyBcX26qbHjnvGRuzgQ-t8xQOBgQrnZv_zQ";
    private static BooksApiService instance;

    private BooksApiService() {
        JSON_FACTORY = GsonFactory.getDefaultInstance(); //tackle json
        //API_KEY = System.getenv("API_KEY");
        //API_KEY = "AIzaSyBcX26qbHjnvGRuzgQ-t8xQOBgQrnZv_zQ";
    }

    public static BooksApiService getInstance() {
        if (instance == null) {
            synchronized (BooksApiService.class) {
                if (instance == null) {
                    BooksApiService.instance = new BooksApiService();
                }
            }
        }
        return instance;
    }

    public static String getApiKey() {
        return API_KEY;
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

}