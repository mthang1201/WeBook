package org.uet.library_management.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.uet.library_management.api.display.RecommendationService;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookService;
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
            RecommendationService recommendationService = new RecommendationService();
            return recommendationService.getRecommendationForUsers(SessionManager.user.getUserId());
        }).thenAccept(books -> {
            Platform.runLater(() -> {
                updateUI(books);
            });
        });

    }
    private void updateUI(List<Book> books) {
        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(
                UIBuilder.generateRecommendation(books).getChildren()
        );
    }

}
