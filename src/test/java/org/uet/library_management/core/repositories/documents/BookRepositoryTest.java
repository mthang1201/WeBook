//package org.uet.library_management.core.repositories.documents;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.uet.library_management.ConnectJDBC;
//import org.uet.library_management.core.entities.documents.Book;
//
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class BookRepositoryTest {
//
//    // Define a BookRepository instance
//    private BookRepository bookRepository;
//
//    // Mock the ConnectJDBC instance for simulation
//    @Mock
//    private ConnectJDBC connectJDBC;
//
//    // Define variables for the test
//    private Book mockBook;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//
//        // Instantiate a new Book for the test
//        mockBook = new Book();
//        // Fill the mockBook object
//        mockBook.setTitle("Title");
//        mockBook.setAuthors("Authors");
//        mockBook.setPublisher("Publisher");
//        mockBook.setPublishedDate("PublishedDate");
//        mockBook.setDescription("Description");
//        mockBook.setIsbn10("Isbn10");
//        mockBook.setIsbn13("Isbn13");
//        mockBook.setPageCount(100);
//        mockBook.setCategories("Category");
//        mockBook.setAverageRating(10.0);
//        mockBook.setRatingsCount(10);
//        mockBook.setImageLinks("ImageLink");
//        mockBook.setLanguage("en");
//        mockBook.setMaturityRating("Mature");
//        mockBook.setPrintType("eBook");
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        bookRepository = null;
//        mockBook = null;
//    }
//
//
//    @Test
//    public void testAddSuccessfully() {
//        // When the "findById" method is called and it returns an empty Optional, simulate it
//        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        // Call "add" method
//        bookRepository.add(mockBook);
//
//        // Verify that "executeUpdate" method was invoked once
//        verify(connectJDBC, Mockito.times(1)).executeUpdate(anyString(), any());
//    }
//
//    @Test
//    public void testAddFailedWhenBookExists() {
//        // When the "findById" method is called and it returns an non-empty Optional, simulate it
//        when(bookRepository.findById(anyString())).thenReturn(Optional.of(mockBook));
//
//        // Call "add" method
//        bookRepository.add(mockBook);
//
//        // Verify that "executeUpdate" method was not invoked
//        verify(connectJDBC, Mockito.never()).executeUpdate(anyString(), any());
//    }
//
//    @Test
//    public void testUpdateSuccessfully() {
//        // When the "findById" method is called and it returns a non-empty Optional, simulate it
//        when(bookRepository.findById(anyString())).thenReturn(Optional.of(mockBook));
//        // Simulate the population of database table
//        bookRepository.update(mockBook);
//        // Verify that "executeUpdate" method was invoked once
//        verify(connectJDBC, Mockito.times(1)).executeUpdate(anyString(), any(), any(), any(), any(), any(), any(),
//                any(), any(), any(), any(), any(), any(), any(), any(), any());
//    }
//
//    @Test
//    public void testUpdateFailedWhenBookDoesNotExist() {
//        // When the "findById" method is called and it returns an empty Optional, simulate it
//        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());
//        // Try to update non-existing book
//        bookRepository.update(mockBook);
//        // Verify that "executeUpdate" method was never invoked
//        verify(connectJDBC, Mockito.never()).executeUpdate(anyString(), any(), any(), any(), any(), any(), any(),
//                any(), any(), any(), any(), any(), any(), any(), any(), any());
//    }
//
//    @Test
//    public void testGetRandomTitlesFromBookmarksWhenNoResultReturned() {
//        // When the "executeQuery" method is called and it returns an empty ResultSet, simulate it
//        when(connectJDBC.executeQuery(anyString())).thenReturn(new MockResultSet());
//
//        // Call "getRandomTitlesFromBookmarks" method
//        List<Book> result = bookRepository.getRandomTitlesFromBookmarks();
//
//        // Assert that the result is empty
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    public void testGetRandomTitlesFromBookmarksWhenResultsReturned() throws SQLException {
//        // Create a Mock ResultSet
//        MockResultSet rs = new MockResultSet("myMock");
//        rs.addColumn("title", Arrays.asList("Title1", "Title2"));
//        rs.addColumn("authors", Arrays.asList("Authors1", "Authors2"));
//        rs.addColumn("publishedDate", Arrays.asList("Date1", "Date2"));
//        rs.addColumn("description", Arrays.asList("Desc1", "Desc2"));
//        rs.addColumn("categories", Arrays.asList("Category1", "Category2"));
//        rs.addColumn("language", Arrays.asList("en", "en"));
//        // Fill in other columns as needed
//
//        // When the "executeQuery" method is called and it returns a filled ResultSet, simulate it
//        when(connectJDBC.executeQuery(anyString())).thenReturn(rs);
//
//        // Call "getRandomTitlesFromBookmarks" method
//        List<Book> result = bookRepository.getRandomTitlesFromBookmarks();
//
//        // Assert that the returned list is not empty and contents are correct
//        assertEquals(2, result.size());
//        assertEquals("Title1", result.get(0).getTitle());
//        assertEquals("Title2", result.get(1).getTitle());
//    }
//
//    @Test
//    public void testGetTopRatedSearchTermBooksWhenNoResults() throws SQLException {
//        // Define the minimum rating
//        double minRating = 4.5;
//        // Define the search term
//        String searchTerm = "Title";
//
//        // When the "executeQueryWithParams" method is called and it returns an empty ResultSet, simulate it
//        when(connectJDBC.executeQueryWithParams(anyString(), any(), any(), any(), any(), anyDouble())).thenReturn(new MockResultSet());
//
//        // Call "getTopRatedSearchTermBooks" method
//        List<Book> result = bookRepository.getTopRatedSearchTermBooks(minRating, searchTerm);
//
//        // Assert that the result is empty as no books meeting the criteria are found
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    public void testGetTopRatedSearchTermBooksWhenResultsReturned() throws SQLException {
//        // Define the minimum rating
//        double minRating = 4.5;
//        // Define the search term
//        String searchTerm = "Title";
//
//        // Create a Mock ResultSet
//        MockResultSet rs = new MockResultSet("myMock");
//        rs.addColumn("title", Arrays.asList("Title1", "Title2"));
//        rs.addColumn("authors", Arrays.asList("Authors1", "Authors2"));
//        rs.addColumn("publishedDate", Arrays.asList("Date1", "Date2"));
//        rs.addColumn("description", Arrays.asList("Desc1", "Desc2"));
//        rs.addColumn("categories", Arrays.asList("Category1", "Category2"));
//        rs.addColumn("language", Arrays.asList("en", "en"));
//        // Fill in other columns as needed
//
//        // When the "executeQuery" method is called and it returns a filled ResultSet, simulate it
//        when(connectJDBC.executeQueryWithParams(anyString(), any(), any(), any(), any(), anyDouble())).thenReturn(rs);
//
//        // Call "getTopRatedSearchTermBooks" method
//        List<Book> result = bookRepository.getTopRatedSearchTermBooks(minRating, searchTerm);
//
//        // Assert that the returned list is not empty and contents are correct
//        assertEquals(2, result.size());
//        assertEquals("Title1", result.get(0).getTitle());
//        assertEquals("Title2", result.get(1).getTitle());
//    }
//
//    @Test
//    public void testUpdatedAverageRatingWhenNoRatingExists() {
//        // Define a non-existing ISBN
//        String isbn = "12345";
//
//        // Simulate the execution by the database server
//        when(connectJDBC.executeQueryWithParams(anyString(), anyString())).thenReturn(new MockResultSet());
//
//        // Call "getUpdatedAverageRating" method
//        double result = bookRepository.getUpdatedAverageRating(isbn);
//
//        // Assert that the result is 0.0 as no ratings are found
//        assertEquals(0.0, result);
//    }
//
//    @Test
//    public void testUpdatedAverageRatingWhenRatingsExists() throws SQLException {
//        // Define an existing ISBN
//        String isbn = "12345";
//
//        // Create a Mock ResultSet
//        MockResultSet rs = new MockResultSet("myMock");
//        rs.addColumn("AVG(rating)", Arrays.asList(4.5));
//
//        // Simulate the execution by the database server
//        when(connectJDBC.executeQueryWithParams(anyString(), anyString())).thenReturn(rs);
//
//        // Call "getUpdatedAverageRating" method
//        double result = bookRepository.getUpdatedAverageRating(isbn);
//
//        // Assert that the returned value is correct
//        assertEquals(4.5, result);
//    }
//}
