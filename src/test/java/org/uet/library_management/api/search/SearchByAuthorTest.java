//package org.uet.library_management.api.search;
//
//import com.google.api.services.books.v1.Books;
//import com.google.api.services.books.v1.model.Volume;
//import com.google.api.services.books.v1.model.Volumes;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.uet.library_management.core.entities.documents.Book;
//import org.uet.library_management.api.BooksApiService;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class SearchByAuthorTest {
//
//    @Test
//    @DisplayName("Test search books by given author name.")
//    void searchBooksByAuthor() throws IOException {
//        // Arrange
//        BooksApiService booksApiServiceMock = mock(BooksApiService.class);
//        Books booksMock = mock(Books.class);
//        Books.Volumes volumesMock = mock(Books.Volumes.class);
//        Books.Volumes.List listMock = mock(Books.Volumes.List.class);
//        Volumes volumesObj = new Volumes();
//        Volume volume = new Volume();
//        volume.setId("testId");
//        volumesObj.setItems(Arrays.asList(volume));
//
//        when(booksApiServiceMock.createQuery()).thenReturn(booksMock);
//        when(listMock.execute()).thenReturn(volumesObj);
//        when(volumesMock.list(any())).thenReturn(listMock);
//        when(booksMock.volumes()).thenReturn(volumesMock);
//
//        SearchByAuthor searchByAuthor = new SearchByAuthor();
//        searchByAuthor.setBooksApiService(booksApiServiceMock);
//
//        // Act
//        List<Book> result = searchByAuthor.search("testAuthor");
//
//        // Assert
//        assertEquals(1, result.size());
//        assertEquals("testId", result.get(0).getId());
//    }
//}