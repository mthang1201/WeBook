package org.uet.library_management.services.api.search;

public class SearchContext {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeSearch(String query) {
        strategy.search(query);
    }
}
