package org.uet.library_management.services.api.search;

import org.uet.library_management.entities.documents.Book;

import java.util.List;

public interface SearchStrategy {
    List<Book> search(String query);
}
