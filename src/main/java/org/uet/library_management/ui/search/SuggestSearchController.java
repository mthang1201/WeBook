package org.uet.library_management.ui.search;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.uet.library_management.api.search.SearchByTitle;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.tools.Mediator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class SuggestSearchController {
    @FXML
    public Label onSearchLabel;

    @FXML
    public VBox topResultsVbox;

    @FXML
    public TextField searchField;

    private Timer timer;

    public void initialize() {

        String searchText = Mediator.getInstance().getText();
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    performSearch(searchText);
                });
            }
        }, 200);
    }

    private void performSearch(String searchText) {
        if (searchText.isEmpty()) {
            topResultsVbox.getChildren().clear();
            return;
        }

        onSearchLabel.setText("Đang hiển thị gợi ý liên quan đến \"" + searchText + "\"");

        CompletableFuture.supplyAsync(() -> {
            SearchContext searchContext = new SearchContext();
            searchContext.setStrategy(new SearchByTitle());
            return searchContext.executeSearch(searchText);
        }).thenAccept(books -> {
            Platform.runLater(() -> {
                updateResults(books);
            });
        });
    }

    private void updateResults(List<Book> books) {
        topResultsVbox.getChildren().clear();
        for (Book book : books) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);

            ImageView imageView = new ImageView(new Image(book.getImageLinks(), true));
            imageView.setFitWidth(55);
            imageView.setFitHeight(83);

            hbox.getChildren().add(imageView);

            VBox vbox = new VBox();
            vbox.setPrefWidth(665);
            vbox.setPrefHeight(83);

            Label titleLabel = new Label(book.getTitle());
            titleLabel.setStyle("-fx-font-weight: bold");
            Label authorsLabel = new Label(book.getAuthors());
            Label averageRatingLabel = new Label(String.format("%.1f", book.getAverageRating()));
            Button addToBookmarkButton = new Button("Get");
            addToBookmarkButton.setOnAction(e -> {
//               BookmarkService bookmarkService = new BookmarkService();
//               bookmarkSerivce.add(Book);
            });

            vbox.getChildren().addAll(titleLabel, authorsLabel, averageRatingLabel, addToBookmarkButton);
            hbox.getChildren().add(vbox);
            topResultsVbox.getChildren().add(hbox);
        }
    }
}