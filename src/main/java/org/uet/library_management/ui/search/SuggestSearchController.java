package org.uet.library_management.ui.search;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.uet.library_management.api.search.SearchByTitle;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.ImageCacheManager;
import org.uet.library_management.tools.Mediator;
import org.uet.library_management.tools.UIBuilder;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SuggestSearchController {
    @FXML public ScrollPane verticalScrollpane;

    @FXML public Label onSearchLabel;
    //    @FXML public TextField searchField;

    @FXML public VBox suggestionsVbox;

    @FXML public HBox inYourLibraryHbox;
    @FXML public ScrollPane horizontalScrollpane;

    @FXML public VBox topResultsVbox;

    private Timer timer;

//    private static final Map<String, List<Book>> booksCache = new LinkedHashMap<>();

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
//            SearchContext searchContext = new SearchContext();
//            searchContext.setStrategy(new SearchByTitle());
//            return searchContext.executeSearch(searchText);
            BookService service = new BookService();

//            if (!booksCache.containsKey(searchText)) {
//                booksCache.put(searchText, service.findByTitle(searchText));
//            }
//            return booksCache.get(searchText);
            return service.findByTitle(searchText);
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
                UIBuilder.generateInYourLibrary(books.subList(0, 15))
        );

        topResultsVbox.getChildren().clear();

        topResultsVbox.getChildren().addAll(
                UIBuilder.generateTopResults(books).getChildren()
        );
    }
}