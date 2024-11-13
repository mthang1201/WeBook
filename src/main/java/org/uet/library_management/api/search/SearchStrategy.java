package org.uet.library_management.api.search;

import org.uet.library_management.core.entities.documents.Book;

import java.util.List;

/**
 * An interface representing a strategy for searching books based on a specific query.
 * Different implementations of this interface can define various search criteria,
 * such as searching by title, author, ISBN, category, or general search terms.
 */
public interface SearchStrategy {
    List<Book> search(String query);
}
