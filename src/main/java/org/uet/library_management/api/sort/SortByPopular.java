package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.util.Comparator;

/**
 * A comparator class used to sort Book objects based on their ratings count in descending order.
 */
public class SortByPopular implements Comparator<Book> {
    /**
     * Compares two Book objects based on their ratings count in descending order.
     *
     * @param b1 The first book to compare.
     * @param b2 The second book to compare.
     * @return A negative integer, zero, or a positive integer as the second book’s ratings count
     *         is greater than, equal to, or less than the first book’s ratings count, respectively.
     */
    @Override
    public int compare(Book b1, Book b2) {
        Integer ratingsCount1 = (b1.getRatingsCount() != null) ? b1.getRatingsCount() : 0;
        Integer ratingsCount2 = (b2.getRatingsCount() != null) ? b2.getRatingsCount() : 0;

        return ratingsCount2.compareTo(ratingsCount1);
    }
}