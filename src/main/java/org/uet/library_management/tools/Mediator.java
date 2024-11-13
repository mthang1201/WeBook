package org.uet.library_management.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.entities.documents.Book;

/**
 * The Mediator class serves as a centralized point for managing static references
 * to various objects such as text, Book, User, and Loan instances in the system.
 * This can be particularly useful for sharing data among different parts of an application.
 */
public class Mediator {
    public static String text;

    public static Book book;

    public static Book bookDetail;

    public static User user;

    public static Loan loan;
}
