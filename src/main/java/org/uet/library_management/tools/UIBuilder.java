package org.uet.library_management.tools;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.BookmarkService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UIBuilder {

    public static FlowPane createFlowPane(List<Book> books) {
        FlowPane flowPane = new FlowPane();

        for (Book book : books) {
            VBox vbox = new VBox();

            Image image = ImageCacheManager.getInstance().loadImage(book.getIsbn13(), book.getTitle(), book.getImageLinks());
//            Image image = new Image(book.getImageLinks(), true);

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);

            Label titleLabel = new Label(book.getTitle());
            Label authorsLabel = new Label(book.getAuthors());

            vbox.getChildren().addAll(imageView, titleLabel, authorsLabel);

            flowPane.getChildren().add(vbox);
        }

        return flowPane;
    }

    public static VBox generateSuggestions(List<String> keywords) {
        VBox suggestionsVbox = new VBox();

        for (String keyword : keywords) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);

            if (!suggestionsVbox.getChildren().isEmpty()) {
                Separator separator = new Separator();
                separator.getStyleClass().add("sub-separator");
                suggestionsVbox.getChildren().add(separator);
            }

            ImageView searchImageView = new ImageView();
            searchImageView.setImage(
                    new Image(
                            UIBuilder.class.getResourceAsStream(
                                    "/org/uet/library_management/icons/search.png"
                            )
                    )
            );
            searchImageView.setFitWidth(16);
            searchImageView.setFitHeight(16);

            Label label = new Label(keyword);
            label.setPadding(new Insets(0, 0, 5, 0));

            hbox.getChildren().addAll(searchImageView, label);

            suggestionsVbox.getChildren().add(hbox);
        }

        return suggestionsVbox;
    }

    public static HBox generateInYourLibrary(List<Book> books) {
        HBox inYourLibraryHbox = new HBox();
        inYourLibraryHbox.setSpacing(20);

        for (Book book : books) {
            ImageView imageView = new ImageView();
            ExecutorService executor = Executors.newFixedThreadPool(5);

            CompletableFuture.supplyAsync(() -> {
                Image image = ImageCacheManager.getInstance().loadImage(
                        book.getIsbn13(),
                        book.getTitle(),
                        book.getImageLinks()
                );

                return image;
            }, executor).thenAccept(image -> {
                Platform.runLater(() -> {
                    imageView.setImage(image);
                    imageView.setFitWidth(75);
                    imageView.setFitHeight(100);
                });
            });

            inYourLibraryHbox.getChildren().add(imageView);
        }

        return inYourLibraryHbox;
    }

    public static VBox generateTopResults(List<Book> books) {
        VBox topResultsVbox = new VBox();
        BookmarkService bookmarkService = new BookmarkService();

        for (Book book : books) {
            //service.add(book);
            HBox hbox = new HBox();
            hbox.setSpacing(10);

            ImageView imageView = new ImageView();
            ExecutorService executor = Executors.newFixedThreadPool(5);

            CompletableFuture.supplyAsync(() -> {
                Image image = ImageCacheManager.getInstance().loadImage(
                        book.getIsbn13(),
                        book.getTitle(),
                        book.getImageLinks()
                );

                return image;
            }, executor).thenAccept(image -> {
                Platform.runLater(() -> {
                    imageView.setImage(image);
                    imageView.setFitWidth(55);
                    imageView.setFitHeight(83);
                });
            });

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
               Bookmark bookmark = new Bookmark(
                       SessionManager.user.getUserId(),
                       book.getIsbn13()
               );
               bookmarkService.add(bookmark);
            });

            vbox.getChildren().addAll(titleLabel, authorsLabel, averageRatingLabel, addToBookmarkButton);
            hbox.getChildren().add(vbox);
            topResultsVbox.getChildren().add(hbox);
        }
        return topResultsVbox;
    }
}
