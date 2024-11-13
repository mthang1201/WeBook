package org.uet.library_management.core.repositories.documents;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.documents.Book;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookRepositoryTest {

    // Define a BookRepository instance
    private BookRepository bookRepository;

    // Mock the ConnectJDBC instance for simulation
    @Mock
    private ConnectJDBC connectJDBC;

    // Define variables for the test
    private Book mockBook;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Instantiate a new Book for the test
        mockBook = new Book();
        // Fill the mockBook object
        mockBook.setTitle("Title");
        mockBook.setAuthors("Authors");
        mockBook.setPublisher("Publisher");
        mockBook.setPublishedDate("PublishedDate");
        mockBook.setDescription("Description");
        mockBook.setIsbn10("Isbn10");
        mockBook.setIsbn13("Isbn13");
        mockBook.setPageCount(100);
        mockBook.setCategories("Category");
        mockBook.setAverageRating(10.0);
        mockBook.setRatingsCount(10);
        mockBook.setImageLinks("ImageLink");
        mockBook.setLanguage("en");
        mockBook.setMaturityRating("Mature");
        mockBook.setPrintType("eBook");
    }

    @AfterEach
    public void cleanUp() {
        bookRepository = null;
        mockBook = null;
    }


    @Test
    public void testAddSuccessfully() {
        // When the "findById" method is called and it returns an empty Optional, simulate it
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());

        // Call "add" method
        bookRepository.add(mockBook);

        // Verify that "executeUpdate" method was invoked once
        verify(connectJDBC, Mockito.times(1)).executeUpdate(anyString(), any());
    }
    
    @Test
    public void testAddFailedWhenBookExists() {
        // When the "findById" method is called and it returns an non-empty Optional, simulate it
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(mockBook));

        // Call "add" method
        bookRepository.add(mockBook);

        // Verify that "executeUpdate" method was not invoked
        verify(connectJDBC, Mockito.never()).executeUpdate(anyString(), any());
    }

    @Test
    public void testUpdateSuccessfully() {
        // When the "findById" method is called and it returns a non-empty Optional, simulate it
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(mockBook));
        // Simulate the population of database table
        bookRepository.update(mockBook);
        // Verify that "executeUpdate" method was invoked once
        verify(connectJDBC, Mockito.times(1)).executeUpdate(anyString(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    public void testUpdateFailedWhenBookDoesNotExist() {
        // When the "findById" method is called and it returns an empty Optional, simulate it
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());
        // Try to update non-existing book
        bookRepository.update(mockBook);
        // Verify that "executeUpdate" method was never invoked
        verify(connectJDBC, Mockito.never()).executeUpdate(anyString(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
}