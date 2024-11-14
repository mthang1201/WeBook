package org.uet.library_management.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.tools.RecommendationGenerator;
import org.uet.library_management.tools.SessionManager;
import org.uet.library_management.tools.UIBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * BookshelfController is responsible for managing the UI interactions
 * and data flow related to the bookshelf view. This class handles the
 * initialization of the UI components and updates the UI with book recommendations.
 */
public class BookshelfController {
    @FXML
    public ScrollPane verticalScrollpane;

    @FXML
    public FlowPane flowPane;


    /**
     * Initializes the BookshelfController by setting up the initial configurations and fetching recommended books.
     *
     * This method sets the padding for the FlowPane and asynchronously fetches book recommendations if they are not
     * already available in the session. Once the recommendations are fetched, the UI is updated on the JavaFX
     * application thread.
     */
    @FXML
    public void initialize() {
        flowPane.setPadding(new Insets(10,10,10,10));

        CompletableFuture.supplyAsync(() -> {
            if (SessionManager.recommendedBooks.isEmpty()) {
                SessionManager.recommendedBooks = RecommendationGenerator.getRecommendationForUsers(SessionManager.user.getUserId());
            }
            return SessionManager.recommendedBooks;
        }).thenAccept(books -> {
            Platform.runLater(() -> {
                updateUI(books);
            });
        });

    }

    /**
     * Updates the UI with a list of books by clearing the current contents
     * of the flow pane and adding the recommendations generated from the provided books.
     *
     * @param books the list of Book objects to be displayed in the UI.
     */
    private void updateUI(List<Book> books) {
//        BookService bookService = new BookService();
//        for (Book book : books) {
//            bookService.add(book);
//        }

        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(
                UIBuilder.generateRecommendation(truncateBook(books)).getChildren()
        );
    }

    /**
     * Truncates the title and authors of each book in the given list to a maximum length of 30 characters.
     * If the title or authors are longer than 30 characters, they are truncated and appended with "...".
     *
     * @param books the list of Book objects to be truncated.
     * @return the list of Book objects with truncated title and authors where applicable.
     */
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
