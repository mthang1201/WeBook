package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.util.Comparator;

/**
 * Comparator implementation for sorting Book objects by their title in alphabetical order.
 * Titles are compared ignoring case.
 * If both titles are null, they are considered equal.
 * If only one title is null, the Book with the non-null title is considered "less than"
 * the one with the null title.
 */
public class SortByAlphabet implements Comparator<Book> {
    /**
     * Compares two Book objects based on their titles in a case-insensitive manner.
     * If both titles are null, they are considered equal.
     * If only one title is null, the Book with the non-null title is considered
     * "less than" the one with the null title.
     *
     * @param b1 the first Book object to be compared.
     * @param b2 the second Book object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(Book b1, Book b2) {
        String title1 = (b1.getTitle() != null) ? b1.getTitle() : null;
        String title2 = (b2.getTitle() != null) ? b2.getTitle() : null;

        if (title1 == null && title2 == null) {
            return 0;
        } else if (title1 == null) {
            return 1;
        } else if (title2 == null) {
            return -1;
        }

        return title1.compareToIgnoreCase(title2);
    }
}