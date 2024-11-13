package org.uet.library_management.tools;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.BookmarkService;
import org.uet.library_management.core.services.DocumentEvaluationService;
import org.uet.library_management.core.services.PreferenceService;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.ui.BookDetailController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The UIBuilder class provides static methods to create various JavaFX UI components
 * based on book and keyword data. It assists in generating elements like FlowPane, VBox,
 * and HBox to display books, suggestions, and library-related information.
 */
public class UIBuilder {

    /**
     * Creates a FlowPane populated with book details. Each book is represented as a VBox containing its cover image,
     * title, and authors. The cover image and title are clickable to open the book detail page.
     *
     * @param books the list of books to be displayed in the flow pane
     * @return a FlowPane object populated with the book details
     */
    public static FlowPane createFlowPane(List<Book> books) {
        FlowPane flowPane = new FlowPane();

        for (Book book : books) {
            VBox vbox = new VBox();

            Image image = ImageCacheManager.getInstance().loadImage(book.getIsbn13(), book.getTitle(), book.getImageLinks());

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);
            imageView.setOnMouseClicked(event -> { openBookDetailPage(book); });

            Label titleLabel = new Label(book.getTitle());
            titleLabel.setOnMouseClicked(event -> { openBookDetailPage(book); });
            Label authorsLabel = new Label(book.getAuthors());

            vbox.getChildren().addAll(imageView, titleLabel, authorsLabel);

            flowPane.getChildren().add(vbox);
        }

        return flowPane;
    }

    /**
     * Generates a VBox containing a list of suggestions based on the provided keywords.
     * Each suggestion is represented by an HBox containing an icon and a keyword label.
     *
     * @param keywords A list of keywords to generate suggestions for.
     * @return A VBox containing the generated suggestions.
     */
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
                    imageView.setOnMouseClicked(event -> { openBookDetailPage(book); });
                });
            });

            inYourLibraryHbox.getChildren().add(imageView);
        }

        return inYourLibraryHbox;
    }

    public static VBox generateTopResults(List<Book> books) {
        VBox topResultsVbox = new VBox();
        BookmarkService bookmarkService = new BookmarkService();
        PreferenceService preferenceService = new PreferenceService();
        BookService bookService = new BookService();

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
                    imageView.setOnMouseClicked(event -> {
                        openBookDetailPage(book);
                    });
                });
            });

            hbox.getChildren().add(imageView);

            VBox vbox = new VBox();
            vbox.setPrefWidth(665);
            vbox.setPrefHeight(83);

            Label titleLabel = new Label(book.getTitle());
            titleLabel.setOnMouseClicked(event -> { openBookDetailPage(book); });

            titleLabel.setStyle("-fx-font-weight: bold");
            Label authorsLabel = new Label(book.getAuthors());
            Label averageRatingLabel = new Label(String.format("%.1f", book.getAverageRating()));
            double updatedAvgRating = bookService.getUpdatedAverageRating(book.getIsbn13());
            book.setAverageRating(updatedAvgRating);
            averageRatingLabel.setText(String.format("%.1f", updatedAvgRating));

            Button addToBookmarkButton = new Button("Get");
            addToBookmarkButton.setOnAction(e -> {
               Bookmark bookmark = new Bookmark(
                       SessionManager.user.getUserId(),
                       book.getIsbn13()
               );
               bookmarkService.add(bookmark);

               BookService service = new BookService();
               service.add(book);

               //add category
                List<String> bookCategory = Arrays.asList(book.getCategories().split(",\\s*"));
                preferenceService.addPreferenceForUser(SessionManager.user.getUserId(), bookCategory);
                openBookDetailPage(book);
            });

            vbox.getChildren().addAll(titleLabel, authorsLabel, averageRatingLabel, addToBookmarkButton);
            hbox.getChildren().add(vbox);
            topResultsVbox.getChildren().add(hbox);
        }
        return topResultsVbox;
    }

    public static FlowPane generateRecommendation(List<Book> books) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(10, 10, 10, 10)); // Set padding for the FlowPane
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPrefWrapLength(600); // Adjust as needed

        ExecutorService executor = Executors.newFixedThreadPool(5); // Thread pool for asynchronous loading

        for (Book book : books) {
            VBox vbox = new VBox();

            ImageView imageView = new ImageView();
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);

            // Load the image asynchronously
            CompletableFuture.supplyAsync(() -> ImageCacheManager.getInstance().loadImage(
                    book.getIsbn13(),
                    book.getTitle(),
                    book.getImageLinks()
            ), executor).thenAccept(image -> {
                Platform.runLater(() -> {
                    imageView.setImage(image);
                    imageView.setOnMouseClicked(event -> {
                        openBookDetailPage(book);
                    });
                });
            });

            Label titleLabel = new Label(book.getTitle());
            titleLabel.setOnMouseClicked(event -> {
                openBookDetailPage(book);
            });

            Label authorsLabel = new Label(book.getAuthors());

            vbox.getChildren().addAll(imageView, titleLabel, authorsLabel);
            flowPane.getChildren().add(vbox);
        }

        return flowPane;
    }

    public static void openBookDetailPage(Book book) {
        Mediator.bookDetail = book;
        SceneManager.getInstance().pushSubScene("bookDetailPage.fxml");
    }
}
