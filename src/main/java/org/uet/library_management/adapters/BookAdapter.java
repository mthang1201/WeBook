package org.uet.library_management.adapters;

import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.core.services.documents.BookService;

public class BookAdapter {
    private BookService bookService;

    private SearchContext searchContext;

    public BookAdapter() {
        this.bookService = new BookService();
        this.searchContext = new SearchContext();
    }


}
