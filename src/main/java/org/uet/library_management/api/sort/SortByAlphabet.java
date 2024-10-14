package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.util.Comparator;

public class SortByAlphabet implements Comparator<Book> {
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