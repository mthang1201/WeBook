package org.uet.library_management.api.display;

import org.junit.jupiter.api.Test;
import org.uet.library_management.api.search.SearchByCategory;
import org.uet.library_management.api.search.SearchByTopRated;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.PreferenceService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecommendationServiceTest {

    @Test
    void getRecommendationForUsers_findsMaximumCategoryAndPerformCategorySearch() {
        // Setup
        PreferenceService preferenceService = mock(PreferenceService.class);
        SearchContext searchContext = mock(SearchContext.class);
        RecommendationService service = new RecommendationService(preferenceService, searchContext);
        List<Book> mockBooks = new ArrayList<>();

        mockBooks.add(new Book());

        when(preferenceService.getMaxCategory(1)).thenReturn("Computer");
        when(searchContext.executeSearch("Computer")).thenReturn(mockBooks);

        // Execute
        List<Book> recommendations = service.getRecommendationForUsers(1);

        // Verify
        verify(searchContext).setStrategy(new SearchByCategory());
        verify(searchContext).executeSearch("Computer");
        assertEquals(mockBooks, recommendations);
    }

    @Test
    void getRecommendationForUsers_fallbackTopRatedSearch_whenCategorySearchReturnsLessThanThree() {
        // Setup
        PreferenceService preferenceService = mock(PreferenceService.class);
        SearchContext searchContext = mock(SearchContext.class);
        RecommendationService service = new RecommendationService(preferenceService, searchContext);
        List<Book> mockBooks = new ArrayList<>();

        mockBooks.add(new Book());

        when(preferenceService.getMaxCategory(1)).thenReturn("Computer");
        when(searchContext.executeSearch("Computer")).thenReturn(mockBooks);
        when(searchContext.executeSearch("fiction")).thenReturn(mockBooks);

        // Execute
        List<Book> recommendations = service.getRecommendationForUsers(1);

        // Verify
        verify(searchContext).setStrategy(new SearchByCategory());
        verify(searchContext).setStrategy(new SearchByTopRated());
        verify(searchContext).executeSearch("Computer");
        verify(searchContext).executeSearch("fiction");
        assertEquals(mockBooks, recommendations);
    }

    @Test
    void getRecommendationForUsers_fallbackTopRatedSearch_whenMaxCategoryIsNull() {
        // Setup
        PreferenceService preferenceService = mock(PreferenceService.class);
        SearchContext searchContext = mock(SearchContext.class);
        RecommendationService service = new RecommendationService(preferenceService, searchContext);
        List<Book> mockBooks = new ArrayList<>();

        mockBooks.add(new Book());

        when(preferenceService.getMaxCategory(1)).thenReturn(null);
        when(searchContext.executeSearch("fiction")).thenReturn(mockBooks);

        // Execute
        List<Book> recommendations = service.getRecommendationForUsers(1);

        // Verify
        verify(searchContext).setStrategy(new SearchByTopRated());
        verify(searchContext).executeSearch("fiction");
        assertEquals(mockBooks, recommendations);
    }
}