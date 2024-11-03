package org.uet.library_management.api.display;

import org.uet.library_management.api.search.SearchByCategory;
import org.uet.library_management.api.search.SearchByTopRated;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.api.search.SearchStrategy;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.PreferenceService;

import java.util.List;

public class RecommendationService {
    private PreferenceService preferenceService;
    private SearchContext searchByCategory;

    public RecommendationService() {
        this.preferenceService = new PreferenceService();
        this.searchByCategory = new SearchContext();
    }

    public List<Book> getRecommendationForUsers(int userId) {
        searchByCategory.setStrategy(new SearchByCategory());
        String maxCategory = preferenceService.getMaxCategory(userId);
        if (maxCategory != null) {
            return searchByCategory.executeSearch(maxCategory);
        } else {
            searchByCategory.setStrategy(new SearchByTopRated());
            return searchByCategory.executeSearch("fiction");
        }
    }
}
