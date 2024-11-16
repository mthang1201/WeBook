package org.uet.library_management.api.display;

import org.uet.library_management.api.search.SearchByCategory;
import org.uet.library_management.api.search.SearchByTopRated;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.api.search.SearchStrategy;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.PreferenceService;

import java.util.List;

/**
 * Service class responsible for providing book recommendations for users
 * based on their reading preferences.
 */
public class RecommendationService {
    private PreferenceService preferenceService;
    private SearchContext searchByCategory;

    /**
     * Constructs a new RecommendationService instance.
     * Initializes the preference service and search context used for generating book recommendations.
     */
    public RecommendationService() {
        this.preferenceService = new PreferenceService();
        this.searchByCategory = new SearchContext();
    }

    /**
     * Provides book recommendations for a given user based on their reading preferences.
     *
     * @param userId the ID of the user for whom book recommendations are to be generated
     * @return a list of recommended books tailored to the user's preferences
     */
    public List<Book> getRecommendationForUsers(int userId) {
        searchByCategory.setStrategy(new SearchByCategory());
        String maxCategory = preferenceService.getMaxCategory(userId);
        if (maxCategory != null) {
            List<Book> books = searchByCategory.executeSearch(maxCategory);
            if (books.size() < 3) {
                searchByCategory.setStrategy(new SearchByTopRated());
                return searchByCategory.executeSearch("fiction");
            }
            else {
                return books;
            }
        } else {
            searchByCategory.setStrategy(new SearchByTopRated());
            return searchByCategory.executeSearch("fiction");
        }
    }
}