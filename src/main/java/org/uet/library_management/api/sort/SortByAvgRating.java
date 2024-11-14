package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.util.Comparator;

/**
 * Comparator implementation for sorting Book objects based on their average rating.
 * This comparator sorts books in descending order of their average ratings.
 * If a book's average rating is null, it is treated as 0.
 */
public class SortByAvgRating implements Comparator<Book> {
    /**
     * Compares two Book objects based on their average ratings.
     * The comparison is done in descending order of average ratings.
     * If a book's average rating is null, it is treated as 0.
     *
     * @param b1 the first Book object to be compared
     * @param b2 the second Book object to be compared
     * @return a negative integer, zero, or a positive integer
     *         as the first argument is less than, equal to, or greater
     *         than the second
     */
    @Override
    public int compare(Book b1, Book b2) {
        Double avgRating1 = (b1.getAverageRating() != null) ? b1.getAverageRating() : 0;
        Double avgRating2 = (b2.getAverageRating() != null) ? b2.getAverageRating() : 0;

        return avgRating2.compareTo(avgRating1);
    }
}