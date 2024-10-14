package org.uet.library_management.api.sort;

import org.uet.library_management.core.entities.documents.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class SortByOldest implements Comparator<Book> {
    private final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    private final SimpleDateFormat fullYearFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat yearOnlyFormat = new SimpleDateFormat("yyyy");

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