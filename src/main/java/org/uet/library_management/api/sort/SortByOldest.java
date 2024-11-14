package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * SortByOldest is a Comparator implementation for sorting Book objects by their published date in ascending order.
 * It supports various date formats including full date-time, date only, and year only.
 */
public class SortByOldest implements Comparator<Book> {
    private final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    private final SimpleDateFormat fullYearFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat yearOnlyFormat = new SimpleDateFormat("yyyy");

    /**
     * Compares two Book objects based on their published dates.
     * If both dates are null, they are considered equal.
     * If one date is null, the Book with the non-null date is considered "less" (indicating it is older).
     * Otherwise, the dates are compared chronologically.
     *
     * @param b1 the first Book to be compared.
     * @param b2 the second Book to be compared.
     * @return a negative integer, zero, or a positive integer as the first Book is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(Book b1, Book b2) {
        Date date1 = parseDate(b1.getPublishedDate());
        Date date2 = parseDate(b2.getPublishedDate());

        if (date1 == null && date2 == null) {
            return 0;
        } else if (date1 == null) {
            return 1;
        } else if (date2 == null) {
            return -1;
        }

        return date1.compareTo(date2);
    }

    /**
     * Parses a date string into a Date object. The method attempts to parse the string
     * in three different formats: full date-time, date only, and year only.
     *
     * @param dateStr the date string to be parsed.
     * @return the parsed Date object, or null if the date string is null, empty, or
     * cannot be parsed in any of the supported formats.
     */
    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        try {
            return fullDateFormat.parse(dateStr);
        } catch (ParseException e) {

        }

        try {
            return fullYearFormat.parse(dateStr);
        } catch (ParseException e) {

        }

        try {
            return yearOnlyFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}