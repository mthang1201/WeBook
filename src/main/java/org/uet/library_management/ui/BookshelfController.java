package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.uet.library_management.api.display.RecommendationService;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.SessionManager;
import org.uet.library_management.tools.UIBuilder;

import java.util.ArrayList;
import java.util.List;

public class BookshelfController {
    @FXML
    public ScrollPane verticalScrollpane;

    @FXML
    public FlowPane flowPane;

//    private static List<Book> booksCache = new ArrayList<>();

    @FXML
    public void initialize() {
        flowPane.setPadding(new Insets(10,10,10,10));

//        SearchContext test = new SearchContext();
//        test.setStrategy(new SearchByAuthor());
//        List<Book> searchTest2 = test.executeSearch("python");
//        searchTest2.sort(new SortByAvgRating());

        BookService service = new BookService();

//        if (booksCache.isEmpty()) {
//            booksCache = service.findAll();
//        }
//        List<Book> books = booksCache;
        RecommendationService recommendationService = new RecommendationService();
        List<Book> books = recommendationService.getRecommendationForUsers(SessionManager.user.getUserId());

        flowPane.getChildren().addAll(
                UIBuilder.createFlowPane(books).getChildren()
        );

    }
}
