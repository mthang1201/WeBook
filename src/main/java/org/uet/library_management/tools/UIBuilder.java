package org.uet.library_management.tools;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.uet.library_management.SceneManager;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.api.search.SearchStrategy;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.BookmarkService;
import org.uet.library_management.core.services.PreferenceService;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.ui.HomeController;

import java.util.ArrayList;
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
            imageView.setPreserveRatio(true);
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

    /**
     * Generates an HBox containing images and click handlers for each book in the provided list.
     * Each book is represented by an ImageView displaying the cover image. Clicking on the image opens the book detail page.
     *
     * @param books the list of books to be displayed in the HBox
     * @return an HBox containing the images of the books
     */
    public static HBox generateInYourLibrary(List<Book> books) {
        HBox inYourLibraryHbox = new HBox();
        inYourLibraryHbox.setSpacing(20);

        for (Book book : books) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(75);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);

            StackPane imageContainer = new StackPane();
            imageContainer.setStyle("-fx-background-color: gray;");
            imageContainer.setPrefSize(75, 100);
            imageContainer.getChildren().add(imageView);

            Task<Image> imageLoadingTask = new Task<Image>() {
                @Override
                protected Image call() throws Exception {
                    return ImageCacheManager.getInstance().loadImage(
                            book.getIsbn13(),
                            book.getTitle(),
                            book.getImageLinks()
                    );
                }
            };

            imageLoadingTask.setOnSucceeded(event -> {
                Image image = imageLoadingTask.getValue();
                imageView.setImage(image);
                imageView.setOnMouseClicked(event2 -> openBookDetailPage(book));
            });

            new Thread(imageLoadingTask).start();

            inYourLibraryHbox.getChildren().add(imageContainer);
        }

        return inYourLibraryHbox;
    }

    /**
     * Generates a VBox containing the top results from a provided list of books. Each book is represented by an HBox
     * containing its cover image, title, authors, average rating, and a button to add it to bookmarks.
     *
     * @param books the list of books to be displayed in the VBox
     * @return a VBox containing the top results of books
     */
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

            imageView.setFitWidth(55);
            imageView.setFitHeight(83);
            imageView.setPreserveRatio(true);

            StackPane imageContainer = new StackPane();
            imageContainer.setStyle("-fx-background-color: gray;");
            imageContainer.setPrefSize(55, 83);

            imageContainer.getChildren().add(imageView);
            Task<Image> loadingImage = new Task<Image>() {
                @Override
                protected Image call() throws Exception {
                    return ImageCacheManager.getInstance().loadImage(
                            book.getIsbn13(),
                            book.getTitle(),
                            book.getImageLinks()
                    );
                }
            };

            loadingImage.setOnSucceeded(event -> {
                imageView.setImage(loadingImage.getValue());
                imageView.setOnMouseClicked(event1 -> {
                    openBookDetailPage(book);
                });
            });

            new Thread(loadingImage).start();

            hbox.getChildren().add(imageContainer);

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

    /**
     * Generates a FlowPane populated with book details. Each book is represented as a VBox containing its cover image,
     * title, and authors. The cover image and title are clickable to open the book detail page.
     *
     * @param books the list of books to be displayed in the flow pane
     * @return a FlowPane object populated with the book details
     */
    public static FlowPane generateRecommendation(List<Book> books) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(10, 10, 10, 10));
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPrefWrapLength(600);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (Book book : books) {
            VBox vbox = new VBox();

            ImageView imageView = new ImageView();
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);

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

    /**
     * Generates a VBox containing a horizontal recommendation list based on the provided search term, header, and search strategy.
     * The list is dynamically created and populated with book recommendations.
     *
     * @param searchTerm      the term used to search for book recommendations
     * @param header          the header title displayed above the recommendation list
     * @param searchStrategy  the strategy applied for searching the books
     * @return a VBox containing the horizontal recommendation list
     */
    public static VBox generateHorizontalRecommendation(String searchTerm, String header, SearchStrategy searchStrategy) {

        SearchContext searchContext = new SearchContext();
        VBox recommendBox = new VBox();
        ScrollPane horizontalScrollpane = new ScrollPane();
        horizontalScrollpane.setFitToWidth(true);
        horizontalScrollpane.setPannable(true);
        horizontalScrollpane.getStyleClass().add("horizontal-scroll-pane");

        HBox titleAndArrowKeyBox = new HBox();
        Label title = new Label();
        Button arrowButton = new Button();
        HBox recommendList = new HBox();
        recommendBox.setStyle("-fx-padding: 5;");
        title.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-family: 'Montserrat Extrabold';"
        );

        arrowButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;"
        );

        recommendList.setStyle(
                "-fx-spacing: 20;" +
                        "-fx-padding: 10 0 0 20;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-family: 'Montserrat SemiBold';" +
                        "-fx-text-overrun: ellipsis;" +
                        "-fx-max-width: 200px;"
        );

        searchContext.setStrategy(searchStrategy);

        title.setText(header);
        arrowButton.setGraphic(LayoutUtils.createImageView(ImageLoaderUtil.getArrowImage(),
                20, 20, true));
        LayoutUtils.setHBoxNodeMargin(title, 20, 0, 0, 23);
        LayoutUtils.setHBoxNodeMargin(arrowButton, 21, 0, 0, 0);

        String cacheKey = searchTerm + header;

        List<Book> books;
        if ("recommendation".equals(searchTerm) && searchStrategy == null) {
            if (SessionManager.defaultRecommendBooks.isEmpty()) {
                SessionManager.defaultRecommendBooks = RecommendationGenerator.getRecommendationForUsers(SessionManager.user.getUserId());
            }
            books = SessionManager.defaultRecommendBooks;
        } else if (SessionManager.cacheBooks.containsKey(cacheKey)) {
            books = SessionManager.cacheBooks.get(cacheKey);
        } else {
            books = searchContext.executeSearch(searchTerm);
            SessionManager.cacheBooks.put(cacheKey, books);
        }

        for (Book book : books) {
            VBox bookBox = new VBox();
            bookBox.setSpacing(10);
            bookBox.setAlignment(Pos.CENTER);

            StackPane imageContainer = new StackPane();
            imageContainer.setStyle("-fx-background-color: gray;");
            imageContainer.setPrefSize(300, 300);

            ImageView bookImageView = new ImageView();
            bookImageView.setCache(true);
            bookImageView.setCacheHint(CacheHint.SPEED);

            bookImageView.setFitWidth(300);
            bookImageView.setFitHeight(300);
            bookImageView.setPreserveRatio(true);

            imageContainer.getChildren().add(bookImageView);

            Task<Image> imageLoadingTask = new Task<Image>() {
                @Override
                protected Image call() throws Exception {
                    return ImageCacheManager.getInstance().loadImage(
                            book.getIsbn13(),
                            book.getTitle(),
                            book.getImageLinks()
                    );
                }
            };

            imageLoadingTask.setOnSucceeded(event -> {
                Image bookImage = imageLoadingTask.getValue();
                bookImageView.setImage(bookImage);
                bookBox.setOnMouseClicked(event2 -> {
                    openBookDetailPage(book);
                });
            });

            imageLoadingTask.setOnFailed(event -> {
                Throwable exception = imageLoadingTask.getException();
                exception.printStackTrace();
            });

            new Thread(imageLoadingTask).start();


            Label bookTitleLabel = new Label(book.getTitle());
            bookTitleLabel.setAlignment(Pos.CENTER);

            bookBox.getChildren().addAll(imageContainer, bookTitleLabel);

            recommendList.getChildren().add(bookBox);
        }
        titleAndArrowKeyBox.getChildren().addAll(title, arrowButton);

        horizontalScrollpane.setContent(recommendList);

        recommendBox.getChildren().addAll(titleAndArrowKeyBox, horizontalScrollpane);
        return recommendBox;
    }



    /**
     * Opens the book detail page for the specified book.
     *
     * @param book the book object for which the detail page is to be opened
     */
    public static void openBookDetailPage(Book book) {
        Mediator.bookDetail = book;
        SceneManager.getInstance().pushSubScene("bookDetailPage.fxml");
    }
}
