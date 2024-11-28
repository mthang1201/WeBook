package org.uet.library_management.ui.search;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.uet.library_management.api.search.SearchByGeneral;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.api.sort.*;
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

    @FXML public ChoiceBox sortBox;

    private Timer timer;

    private List<Book> combinedResults;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * - Configures the vertical scroll pane to fit its width and be pannable.
     * - Retrieves the search text from the Mediator class.
     * - Cancels any ongoing timer if it exists.
     * - Schedules a new timer task to perform a search after a fixed delay.
     */
    @FXML
    public void initialize() {
        combinedResults = new ArrayList<>();
        verticalScrollpane.setFitToWidth(true);
        verticalScrollpane.setPannable(true);
        sortBox.setValue("Sort By");
        sortBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && oldValue.equals("Sort by")) {
                sortBox.getItems().remove("Sort by");
            }
        });

        sortBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Alphabet")) {
                combinedResults.sort(new SortByAlphabet());
            } else if (newValue.equals("AvgRating")) {
                combinedResults.sort(new SortByAvgRating());
            } else if (newValue.equals("Newest")) {
                combinedResults.sort(new SortByNewest());
            } else if (newValue.equals("Oldest")) {
                combinedResults.sort(new SortByOldest());
            } else {
                combinedResults.sort(new SortByPopular());
            }
            updateUI(combinedResults);
        });
        
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
                    searchFromDb(searchText);
                });
            }
        }, 500);
    }

    /**
     * Searches for books based on the given search text, retrieves top rated book results
     * and general search results, updates their ratings, combines the results,
     * and sorts them by average rating, and finally updates the UI with the results.
     *
     * @param searchText The text to search for books.
     */
    private void performSearch(String searchText) {
        if (searchText.isEmpty()) {
            topResultsVbox.getChildren().clear();
            return;
        }

        onSearchLabel.setText("Đang hiển thị gợi ý liên quan đến \"" + searchText + "\"");

        Task<List<Book>> searchTask = new Task<List<Book>>() {
            @Override
            protected List<Book> call() throws Exception {
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
            }
        };

        searchTask.setOnSucceeded(event -> {
            List<Book> combinedResults = searchTask.getValue();
            updateUI(combinedResults);
        });

        new Thread(searchTask).start();
    }


    private void searchFromDb(String searchText) {
        if (searchText.isEmpty()) {
            inYourLibraryHbox.getChildren().clear();
            return;
        }

        Task<List<Book>> loadingBooksFromDb = new Task<List<Book>>() {
            @Override
            protected List<Book> call() throws Exception {
                BookService bookService = new BookService();
                return bookService.getTitlesFromBookmarks(searchText);
            }
        };

        loadingBooksFromDb.setOnSucceeded(event -> {
            List<Book> books = loadingBooksFromDb.getValue();
            inYourLibraryHbox.getChildren().addAll(
                    UIBuilder.generateInYourLibrary(books)
            );
        });
        new Thread (loadingBooksFromDb).start();

    }
    /**
     * Updates the user interface with the provided list of books, including suggestions,
     * books in the user's library, and top results.
     *
     * @param books A list of Book objects to be displayed in the UI.
     */
    private void updateUI(List<Book> books) {
//        BookService service = new BookService();

//        suggestionsVbox.getChildren().clear();
//
//        suggestionsVbox.getChildren().addAll(
//                UIBuilder.generateSuggestions(
//                        service.suggest("searchText")
//                )
//        );



        topResultsVbox.getChildren().clear();

        topResultsVbox.getChildren().addAll(
                UIBuilder.generateTopResults(books).getChildren()
        );
    }
}