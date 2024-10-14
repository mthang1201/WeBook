package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.util.Comparator;

public class SortByPopular implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        Integer ratingsCount1 = (b1.getRatingsCount() != null) ? b1.getRatingsCount() : 0;
        Integer ratingsCount2 = (b2.getRatingsCount() != null) ? b2.getRatingsCount() : 0;

        return ratingsCount2.compareTo(ratingsCount1);
    }
}