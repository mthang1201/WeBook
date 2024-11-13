package org.uet.library_management.core.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.Bookmark;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

public class BookmarkRepositoryTest {

    @Mock
    ConnectJDBC connectJDBC;

    @Mock
    ResultSet resultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllByPage() throws SQLException {
        BookmarkRepository bookmarkRepository = new BookmarkRepository();
        Bookmark bookmark1 = new Bookmark();
        Bookmark bookmark2 = new Bookmark();
        bookmark1.setIsbn13("978-3-16-148410-0");
        bookmark1.setUserId(1);
        bookmark2.setIsbn13("978-1-60309-427-6");
        bookmark2.setUserId(2);
        List<Bookmark> bookmarks = new ArrayList<>(Arrays.asList(bookmark1, bookmark2));

        when(connectJDBC.executeQuery("SELECT * FROM bookmarks LIMIT 2 OFFSET 0")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("userId")).thenReturn(1).thenReturn(2);
        when(resultSet.getString("isbn13")).thenReturn("978-3-16-148410-0").thenReturn("978-1-60309-427-6");

        Mockito.doReturn(connectJDBC).when(bookmarkRepository).getConnectJDBC();

        List<Bookmark> actualBookmarks = bookmarkRepository.findAllByPage(1, 2);

        assertEquals(2, actualBookmarks.size());
        assertEquals(bookmarks, actualBookmarks);
        Mockito.verify(connectJDBC, times(1)).executeQuery("SELECT * FROM bookmarks LIMIT 2 OFFSET 0");
    }

    @Test
    void testFindAllByPage_withEmptyResultSet() throws SQLException {
        BookmarkRepository bookmarkRepository = new BookmarkRepository();
        List<Bookmark> expectedBookmarks = new ArrayList<>();

        when(connectJDBC.executeQuery("SELECT * FROM bookmarks LIMIT 2 OFFSET 0")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Bookmark> actualBookmarks = bookmarkRepository.findAllByPage(1, 2);

        assertEquals(0, actualBookmarks.size());
        assertEquals(expectedBookmarks, actualBookmarks);
        Mockito.verify(connectJDBC, times(1)).executeQuery("SELECT * FROM bookmarks LIMIT 2 OFFSET 0");
    }

    @Test
    void testCountAll() throws SQLException {
        BookmarkRepository bookmarkRepository = new BookmarkRepository();

        when(connectJDBC.executeQuery("SELECT COUNT(*) AS total FROM bookmarks")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("total")).thenReturn(10);

        int result = bookmarkRepository.countAll();

        assertEquals(10, result);
        Mockito.verify(connectJDBC, times(1)).executeQuery("SELECT COUNT(*) AS total FROM bookmarks");
    }

    @Test
    void testCountAll_withSQLExceptionThrown() throws SQLException {
        BookmarkRepository bookmarkRepository = new BookmarkRepository();

        when(connectJDBC.executeQuery("SELECT COUNT(*) AS total FROM bookmarks")).thenThrow(SQLException.class);

        Mockito.doReturn(connectJDBC).when(bookmarkRepository).getConnectJDBC();

        assertThrows(RuntimeException.class, bookmarkRepository::countAll);
        Mockito.verify(connectJDBC, times(1)).executeQuery("SELECT COUNT(*) AS total FROM bookmarks");
    }
}