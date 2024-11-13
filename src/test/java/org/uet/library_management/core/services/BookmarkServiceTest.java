package org.uet.library_management.core.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.repositories.BookmarkRepository;

public class BookmarkServiceTest {

    @Test
    void updateShouldCallUpdateOnRepository_WhenGivenBookmark() {
        // Arrange
        Bookmark bookmark = new Bookmark();
        BookmarkRepository mockBookmarkRepository = Mockito.mock(BookmarkRepository.class);
        Mockito.doNothing().when(mockBookmarkRepository).update(bookmark);
        BookmarkService bookmarkService = new BookmarkService();

        // Act
        bookmarkService.update(bookmark);

        // Assert
        Mockito.verify(mockBookmarkRepository, Mockito.times(1)).update(bookmark);
    }

    @Test
    void updateShouldNotThrowException_WhenGivenBookmark() {
        // Arrange
        Bookmark bookmark = new Bookmark();
        BookmarkRepository mockBookmarkRepository = Mockito.mock(BookmarkRepository.class);
        Mockito.doNothing().when(mockBookmarkRepository).update(bookmark);
        BookmarkService bookmarkService = new BookmarkService();

        // Act + Assert
        Mockito.doNothing().when(() -> bookmarkService.update(bookmark));
    }
}