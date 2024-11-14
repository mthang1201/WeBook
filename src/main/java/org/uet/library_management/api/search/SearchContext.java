package org.uet.library_management.api.search;

import org.uet.library_management.core.entities.documents.Book;

import java.util.List;

/**
 * The SearchContext class is responsible for handling the search logic using a specified strategy.
 * It uses the strategy pattern to allow various search implementations to be pluggable.
 */
public class SearchContext {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Book> executeSearch(String query) {
        return strategy.search(query);
    }
}
