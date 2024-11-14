package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * This class provides a comparator for sorting books based on their publication dates,
 * from the newest to the oldest. It implements the Comparator interface to compare
 * Book objects.
 */
public class SortByNewest implements Comparator<Book> {
    private final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    private final SimpleDateFormat fullYearFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat yearOnlyFormat = new SimpleDateFormat("yyyy");

    /**
     * Compares two Book objects based on their publication dates.
     *
     * @param b1 the first Book to be compared.
     * @param b2 the second Book to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is newer than,
     *         equal to, or older than the second, respectively. If both dates are null, returns 0.
     *         If only the first date is null, returns 1. If only the second date is null, returns -1.
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

        return date2.compareTo(date1);
    }

    /**
     * Parses the given date string into a Date object using different date formats.
     * The method attempts to parse the date string in the following order:
     * full date-time format, full date format, and year-only format.
     *
     * @param dateStr the date string to be parsed.
     * @return the parsed Date object or null if the date string is invalid or cannot be parsed.
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