package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.BookmarkService;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.SessionManager;
import org.uet.library_management.tools.UIBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        BookmarkService bookmarkService = new BookmarkService();
        BookService bookService = new BookService();

//        if (booksCache.isEmpty()) {
//            booksCache = service.findAll();
//        }
//        List<Book> books = booksCache;
        List<Bookmark> bookmarks = bookmarkService.findByUserId(SessionManager.user.getUserId());
        List<Book> books = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            Optional<Book> book = bookService.findById(bookmark.getDocumentId());

            book.ifPresent(books::add);
        }

        if (books.isEmpty()) {
            verticalScrollpane.setVisible(false);
            noBooksVbox.setVisible(true);
        } else {
            verticalScrollpane.setVisible(true);
            noBooksVbox.setVisible(false);
            noBooksVbox.setManaged(false);

            flowPane.setPadding(new Insets(10,10,10,10));
            flowPane.getChildren().addAll(
                    UIBuilder.createFlowPane(books).getChildren()
            );
        }
    }
}
