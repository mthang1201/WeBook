package org.uet.library_management.api;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.books.v1.Books;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Singleton service to interact with the Google Books API.
 * Provides methods to create and execute queries for book search.
 */
public class BooksApiService {

    private static final String APPLICATION_NAME = "WeBooks";
    private static final String API_KEY = "AIzaSyBFeSPdr4seMW1NpLK72GPHVgIveJowPzo";
    private static BooksApiService instance;
    private final JsonFactory jsonFactory;

    /**
     * Private constructor to initialize the BooksApiService.
     * Sets up the JSON_FACTORY instance using GsonFactory to handle JSON operations.
     */
    private BooksApiService() {
        this.jsonFactory = GsonFactory.getDefaultInstance();
    }

    /**
     * Provides a global point of access to the singleton instance of BooksApiService.
     * This method ensures that only one instance of BooksApiService is created,
     * using double-checked locking for thread safety.
     *
     * @return the singleton instance of BooksApiService
     */
    public static BooksApiService getInstance() {
        if (instance == null) {
            synchronized (BooksApiService.class) {
                if (instance == null) {
                    instance = new BooksApiService();
                }
            }
        }
        return instance;
    }

    /**
     * Retrieves the API key used for accessing the Google Books API.
     *
     * @return the API key as a String.
     */
    public static String getApiKey() {
        return API_KEY;
    }

    /**
     * Creates and returns a configured instance of the Books API client.
     * The client is initialized with transport, JSON factory, and application
     * name settings required to interact with the Google Books API.
     *
     * @return a configured Books API client or null if initialization fails due to exceptions.
     */
    public Books createQuery() {
        try {
            return new Books.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
