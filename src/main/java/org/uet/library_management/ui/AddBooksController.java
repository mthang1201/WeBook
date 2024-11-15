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

/**
 * Controller class responsible for handling the addition of books via the GUI.
 * It interacts with text fields for ISBN input and an add button to initiate the process.
 */
public class AddBooksController {
    @FXML
    public TextField isbn10TextField;

    @FXML
    public TextField isbn13TextField;

    @FXML
    public Button addButton;

    /**
     * Handles the Add button click event to add a book based on the given ISBN input.
     *
     * Retrieves ISBN from either the isbn10TextField or isbn13TextField.
     * If a valid ISBN is provided, it searches for the book using the SearchContext with SearchByISBN strategy.
     * If the book is found, the first book from the search results is added to the library using the BookService.
     * Displays a success message if the book is added successfully, otherwise shows a failure message.
     */
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

    /**
     * Displays a success message indicating that a book has been successfully added.
     *
     * This method triggers an alert with a predefined title and message,
     * informing the user that a new book has been added to the library.
     * It uses the `AlertUtil` class to show a warning alert with the specified text.
     */
    private void printSuccessMessage() {
        AlertUtil.showWarningAlert(
                "Successfully added Book",
                "You have added a new book!",
                null,
                null
        );
    }

    /**
     * Displays a failure message when the book addition process does not find the book in the library.
     *
     * This method triggers an alert with a predefined title and message,
     * informing the user that the attempted book addition has failed because the book does not exist in the library.
     * It uses the `AlertUtil` class to show a warning alert with the specified text.
     */
    private void printFailedMessage() {
        AlertUtil.showWarningAlert(
                "Failed to add Book",
                "Your book does not exist in our library!",
                null,
                null
        );
    }
}
