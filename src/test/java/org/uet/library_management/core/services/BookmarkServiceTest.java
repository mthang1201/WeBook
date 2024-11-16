package org.uet.library_management.core.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.repositories.BookmarkRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookmarkServiceTest {
    private BookmarkService service;

    @Mock
    private BookmarkRepository mockRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new BookmarkService(mockRepository);
    }

    @Test
    public void testFindAll() {
        // Mock behavior
        List<Bookmark> mockSamples = new ArrayList<>();
        mockSamples.add(new Bookmark(1, "1234567890123"));
        mockSamples.add(new Bookmark(2, "9876543210987"));

        when(mockRepository.findAll()).thenReturn(mockSamples);

        // Execute
        List<Bookmark> result = service.findAll();

        // Verify
        assertEquals(2, result.size());
        Bookmark bookmark = result.get(0);
        assertEquals(1, bookmark.getUserId());
        assertEquals("1234567890123", bookmark.getIsbn13());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    public void testFindByUserId() {
        // Mock behavior
        int mockUserId = 1;

        List<Bookmark> mockSamples = new ArrayList<>();
        mockSamples.add(new Bookmark(1, "1234567890123"));
        mockSamples.add(new Bookmark(1, "9876543210987"));

        when(mockRepository.findByUserId(mockUserId)).thenReturn(mockSamples);

        // Execute
        List<Bookmark> result = service.findByUserId(mockUserId);

        // Verify
        assertEquals(2, result.size());
        Bookmark bookmark = result.get(0);
        assertEquals(1, bookmark.getUserId());
        assertEquals("1234567890123", bookmark.getIsbn13());
        verify(mockRepository, times(1)).findByUserId(mockUserId);
    }

    @Test
    public void testCountAll() {
        // Mock behavior
        when(mockRepository.countAll()).thenReturn(5);

        // Execute
        int count = service.countAll();

        // Verify
        assertEquals(5, count);
        verify(mockRepository, times(1)).countAll();
    }

    @Test
    public void testAdd() {
        // Mock behavior
        Bookmark mockBookmark = new Bookmark(1, "1234567890123");

        // Execute
        doNothing().when(mockRepository).add(mockBookmark);

        service.add(mockBookmark);

        // Verify
        verify(mockRepository, times(1)).add(mockBookmark);
    }

    @Test
    public void testUpdate() {
        // Mock behavior
        Bookmark mockBookmark = new Bookmark(1, "1234567890123");

        // Execute
        doNothing().when(mockRepository).update(mockBookmark);

        service.update(mockBookmark);

        // Verify
        verify(mockRepository, times(1)).update(mockBookmark);
    }

    @Test
    public void testRemove() {
        // Mock behavior
        Bookmark mockBookmark = new Bookmark(1, "1234567890123");

        // Execute
        doNothing().when(mockRepository).remove(mockBookmark);

        service.remove(mockBookmark);

        // Verify
        verify(mockRepository, times(1)).remove(mockBookmark);
    }
}