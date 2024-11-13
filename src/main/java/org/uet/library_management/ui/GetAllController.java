package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.uet.library_management.api.search.SearchByAuthor;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.api.sort.SortByAvgRating;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.repositories.documents.BookRepository;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.UIBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * The GetAllController class is responsible for initializing the UI by populating a FlowPane
 * with a list of books obtained from the BookRepository.
 * It manages interactions involving the verticalScrollpane and flowPane UI components.
 */
public class GetAllController {
    @FXML
    public ScrollPane verticalScrollpane;

    @FXML
    public FlowPane flowPane;

    @FXML
    public void initialize() {
        flowPane.setPadding(new Insets(10,10,10,10));

        BookRepository repository = new BookRepository();
        List<Book> books = repository.getBooksFromLoans();

        flowPane.getChildren().addAll(
                UIBuilder.createFlowPane(books).getChildren()
        );

    }
}
