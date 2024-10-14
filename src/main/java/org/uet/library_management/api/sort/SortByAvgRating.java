package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.util.Comparator;

public class SortByAvgRating implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        Double avgRating1 = (b1.getAverageRating() != null) ? b1.getAverageRating() : 0;
        Double avgRating2 = (b2.getAverageRating() != null) ? b2.getAverageRating() : 0;

        return avgRating2.compareTo(avgRating1);
    }
}