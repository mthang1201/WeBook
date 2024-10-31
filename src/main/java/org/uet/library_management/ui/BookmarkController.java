package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookmarkService;
import org.uet.library_management.tools.UIBuilder;

import java.util.ArrayList;
import java.util.List;

public class BookmarkController {
    @FXML
    public ScrollPane verticalScrollpane;

    @FXML
    public FlowPane flowPane;

    @FXML
    public VBox noBooksVbox;

//    private static List<Book> booksCache = new ArrayList<>();

    @FXML
    public void initialize() {
        BookmarkService service = new BookmarkService();

//        if (booksCache.isEmpty()) {
//            booksCache = service.findAll();
//        }
//        List<Book> books = booksCache;
        List<Book> books = service.findAll();

        if (books.isEmpty()) {
            verticalScrollpane.setVisible(false);
            noBooksVbox.setVisible(true);
        } else {
            verticalScrollpane.setVisible(true);
            noBooksVbox.setVisible(false);

            flowPane.setPadding(new Insets(10,10,10,10));
            flowPane.getChildren().addAll(
                    UIBuilder.createFlowPane(books).getChildren()
            );
        }

    }
}
