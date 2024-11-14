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
