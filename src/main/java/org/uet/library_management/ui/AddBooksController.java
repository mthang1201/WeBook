package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.uet.library_management.api.search.SearchByISBN;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.AlertUtil;

import java.util.List;

public class AddBooksController {
    @FXML
    public TextField isbn10TextField;

    @FXML
    public TextField isbn13TextField;

    @FXML
    public Button addButton;

    @FXML
    public void handleAddButton() {
        String isbn = "";
        if (!isbn10TextField.getText().isEmpty()) {
            isbn = isbn10TextField.getText();
        }
        if (!isbn13TextField.getText().isEmpty()) {
            isbn = isbn13TextField.getText();
        }
        if (isbn.isEmpty()) {
            return;
        }

        SearchContext searchContext = new SearchContext();
        SearchByISBN searchByISBN = new SearchByISBN();
        searchContext.setStrategy(searchByISBN);

        List<Book> books = searchContext.executeSearch(isbn);
        if (books.isEmpty()) {
            printFailedMessage();
        } else {
            BookService bookService = new BookService();
            bookService.add(books.get(0));

            printSuccessMessage();
        }
    }

    private void printSuccessMessage() {
        AlertUtil.showWarningAlert(
                "Successfully added Book",
                "You have added a new book!",
                null,
                null
        );
    }

    private void printFailedMessage() {
        AlertUtil.showWarningAlert(
                "Failed to add Book",
                "Your book does not exist in our library!",
                null,
                null
        );
    }
}
