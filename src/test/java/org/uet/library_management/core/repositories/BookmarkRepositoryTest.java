package org.uet.library_management.core.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.Bookmark;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookmarkRepositoryTest {
    private BookmarkRepository repository;

    @Mock
    private ConnectJDBC mockConnectJDBC;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new BookmarkRepository(mockConnectJDBC);
    }

    @Test
    void testFindAll() throws SQLException {
        // Mock behavior
        String query = "SELECT * FROM bookmarks";
        when(mockConnectJDBC.executeQuery(query)).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("userId")).thenReturn(1, 2);
        when(mockResultSet.getString("isbn13")).thenReturn("1234567890123", "9876543210987");

        // Execute
        List<Bookmark> bookmarks = repository.findAll();

        // Verify
        assertEquals(2, bookmarks.size());
        Bookmark bookmark = bookmarks.get(0);
        assertEquals(1, bookmark.getUserId());
        assertEquals("1234567890123", bookmark.getIsbn13());
        verify(mockConnectJDBC, times(1)).executeQuery(query);
    }

    @Test
    void testFindByUserId() throws SQLException {
        // Mock behavior
        String query = "SELECT * FROM bookmarks WHERE userId = ?";
        when(mockConnectJDBC.executeQueryWithParams(query, 1)).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("userId")).thenReturn(1, 2);
        when(mockResultSet.getString("isbn13")).thenReturn("1234567890123", "9876543210987");

        // Execute
        List<Bookmark> bookmarks = repository.findByUserId(1);

        // Verify
        assertEquals(2, bookmarks.size());
        Bookmark bookmark = bookmarks.get(0);
        assertEquals(1, bookmark.getUserId());
        assertEquals("1234567890123", bookmark.getIsbn13());
        verify(mockConnectJDBC, times(1)).executeQueryWithParams(query, 1);
    }

    @Test
    void testAdd() {
        // Mock behavior
        String query = "INSERT INTO bookmarks (userId, isbn13) VALUES (?, ?)";
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(1);
        bookmark.setIsbn13("123456789");

        doNothing().when(mockConnectJDBC).executeUpdate(anyString(), any());

        // Execute
        repository.add(bookmark);

        // Verify
        verify(mockConnectJDBC, times(1)).executeUpdate(
                eq(query),
                eq(1),
                eq("123456789")
        );
    }

    @Test
    void testRemove() {
        // Mock behavior
        String query = "DELETE FROM bookmarks WHERE userId = ? AND isbn13 = ?";
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(1);
        bookmark.setIsbn13("123456789");

        doNothing().when(mockConnectJDBC).executeUpdate(anyString(), any());

        // Execute
        repository.remove(bookmark);

        // Verify
        verify(mockConnectJDBC, times(1)).executeUpdate(
                eq(query),
                eq(1),
                eq("123456789")
        );
    }

    @Test
    void testCountAll() throws SQLException {
        // Mock behavior
        String query = "SELECT COUNT(*) AS total FROM bookmarks";
        when(mockConnectJDBC.executeQuery(query)).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("total")).thenReturn(5);

        doNothing().when(mockConnectJDBC).executeUpdate(anyString(), any());

        // Execute
        int count = repository.countAll();

        // Verify
        assertEquals(5, count);
        verify(mockConnectJDBC, times(1)).executeQuery(query);
    }
}