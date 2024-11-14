package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.repositories.documents.BookRepository;
import org.uet.library_management.core.services.BookmarkService;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.SessionManager;
import org.uet.library_management.tools.UIBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The BookmarkController class is a JavaFX controller responsible for managing the UI components
 * and behavior for bookmarks within the application. It initializes the bookmark view based
 * on the user's bookmarked books.
 */
public class BookmarkController {
    @FXML
    public ScrollPane verticalScrollpane;

    @FXML
    public FlowPane flowPane;

    @FXML
    public VBox noBooksVbox;

//    private static List<Book> booksCache = new ArrayList<>();

    @FXML
    public void initialize() {
        BookRepository bookRepository = new BookRepository();
        List<Book> books = bookRepository.getBooksFromBookmarks();

        if (books.isEmpty()) {
            verticalScrollpane.setVisible(false);
            noBooksVbox.setVisible(true);
        } else {
            verticalScrollpane.setVisible(true);
            noBooksVbox.setVisible(false);
            noBooksVbox.setManaged(false);

            flowPane.setPadding(new Insets(10,10,10,10));
            FlowPane getFlowPane = UIBuilder.createFlowPane(truncateBook(books));
            flowPane.getChildren().addAll(getFlowPane.getChildren());

        }
    }

    private List<Book> truncateBook(List<Book> books) {
        for (Book book : books) {
            if (book.getTitle() != null && book.getTitle().length() > 30) {
                book.setTitle(book.getTitle().substring(0, 30) + "...");
            }

            if (book.getAuthors() != null && book.getAuthors().length() > 30){
                book.setAuthors(book.getAuthors().substring(0, 30) + "...");
            }
        }
        return books;
    }
}
