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

public class BookshelfController {
    @FXML
    public ScrollPane verticalScrollpane;

    @FXML
    public FlowPane flowPane;


    @FXML
    public void initialize() {
        flowPane.setPadding(new Insets(10,10,10,10));

        CompletableFuture.supplyAsync(() -> {
            return RecommendationGenerator.getRecommendationForUsers(SessionManager.user.getUserId());
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
                UIBuilder.generateRecommendation(books).getChildren()
        );
    }

}
