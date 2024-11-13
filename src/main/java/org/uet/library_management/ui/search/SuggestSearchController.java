package org.uet.library_management.ui.search;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.uet.library_management.api.search.SearchByGeneral;
import org.uet.library_management.api.search.SearchByTitle;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.api.sort.SortByAvgRating;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.Mediator;
import org.uet.library_management.tools.UIBuilder;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Controller class for handling search suggestions and search-related functionalities
 * in the application. It manages the UI components related to search suggestions
 * and search results, and performs asynchronous searches to update the UI accordingly.
 */
public class SuggestSearchController {
    @FXML public ScrollPane verticalScrollpane;

    @FXML public Label onSearchLabel;
    //    @FXML public TextField searchField;

    @FXML public VBox suggestionsVbox;

    @FXML public HBox inYourLibraryHbox;
    @FXML public ScrollPane horizontalScrollpane;

    @FXML public VBox topResultsVbox;

    private Timer timer;


    @FXML
    public void initialize() {
        verticalScrollpane.setFitToWidth(true);
        verticalScrollpane.setPannable(true);
        
        String searchText = Mediator.text;
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
            BookService bookService = new BookService();
            List<Book> topRatedBooks = bookService.getTopRatedSearchTermBooks(1.0, searchText);

            SearchContext searchContext = new SearchContext();
            searchContext.setStrategy(new SearchByGeneral());
            List<Book> generalSearchResults = searchContext.executeSearch(searchText);

            Set<String> existingIsbns = new HashSet<>();
            List<Book> combinedResults = new ArrayList<>();

            for (Book book : topRatedBooks) {
                double updatedAvgRating = bookService.getUpdatedAverageRating(book.getIsbn13());
                book.setAverageRating(updatedAvgRating);
                if (!existingIsbns.contains(book.getIsbn13())) {
                    combinedResults.add(book);
                    existingIsbns.add(book.getIsbn13());
                }
            }

            for (Book book : generalSearchResults) {
                double updatedAvgRating = bookService.getUpdatedAverageRating(book.getIsbn13());
                book.setAverageRating(updatedAvgRating);
                if (!existingIsbns.contains(book.getIsbn13())) {
                    combinedResults.add(book);
                    existingIsbns.add(book.getIsbn13());
                }
            }

            combinedResults.sort(new SortByAvgRating());

            return combinedResults;
        }).thenAccept(books -> {
            Platform.runLater(() -> {
                updateUI(books);
            });
        });
    }

    private void updateUI(List<Book> books) {
        BookService service = new BookService();

        suggestionsVbox.getChildren().clear();

        suggestionsVbox.getChildren().addAll(
                UIBuilder.generateSuggestions(
                        service.suggest("searchText")
                )
        );

        inYourLibraryHbox.getChildren().clear();

        inYourLibraryHbox.getChildren().addAll(
                UIBuilder.generateInYourLibrary(books)
        );

        topResultsVbox.getChildren().clear();

        topResultsVbox.getChildren().addAll(
                UIBuilder.generateTopResults(books).getChildren()
        );
    }
}