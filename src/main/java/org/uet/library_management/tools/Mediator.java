package org.uet.library_management.tools;

import org.uet.library_management.core.entities.documents.Book;

public class Mediator {
    public static String text;

    public static Book book;

    private static Mediator instance;

    private Mediator() {}

    public static Mediator getInstance() {
        if (instance == null) {
            instance = new Mediator();
        }
        return instance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        Mediator.text = text;
    }

    public Book getCachedBook() {
        return book;
    }

    public void setCachedBook(Book book) {
        Mediator.book = book;
    }
}
