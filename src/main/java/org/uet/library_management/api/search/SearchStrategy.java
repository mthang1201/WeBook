package org.uet.library_management.api.search;

import org.uet.library_management.core.entities.documents.Book;

import java.util.List;

public interface SearchStrategy {
    List<Book> search(String query);
}
