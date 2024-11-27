package org.uet.library_management.ui.admin.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.Mediator;

/**
 * Controller class for handling book form related operations in a JavaFX application.
 * This class interacts with the user interface elements related to book details,
 * processes form submissions, and manages the data transfer to and from the BookService.
 */
public class BookFormController {
    private BookService service;

    @FXML private TextField titleField;
    @FXML private TextField authorsField;
    @FXML private TextField isbn13Field;
    @FXML private TextField descriptionField;
    @FXML private TextField categoriesField;

    @FXML private Label errorWarning;

    private boolean createMethod = true;

    /**
     * Initializes the form with book details if a book instance exists in the Mediator.
     * This method sets up the BookService and pre-fills the form fields with data
     * from the Mediator's book instance.
     * It also toggles the createMethod flag based on whether a book is being edited or created.
     */
    @FXML
    private void initialize() {
        service = new BookService();

        Book book = Mediator.book;
        if (book != null) {
            createMethod = false;
            titleField.setText(book.getTitle());
            authorsField.setText(book.getAuthors());
            isbn13Field.setText(book.getIsbn13());
            descriptionField.setText(book.getDescription());
            categoriesField.setText(book.getCategories());
        }
    }

    /**
     * Checks if any of the form fields are invalid.
     * A field is considered invalid if it is either null or empty after trimming.
     *
     * @return true if any of the fields (title, authors, isbn13, description, categories) are invalid; false otherwise.
     */
    private boolean invalidField() {
        if (titleField.getText() == null || titleField.getText().trim().equals("")) { return true; }
        if (authorsField.getText() == null || authorsField.getText().trim().equals("")) { return true; }
        if (isbn13Field.getText() == null || isbn13Field.getText().trim().equals("")) { return true; }
        if (descriptionField.getText() == null || descriptionField.getText().trim().equals("")) { return true; }
        if (categoriesField.getText() == null || categoriesField.getText().trim().equals("")) { return true; }
        return false;
    }

    /**
     * Navigates back to the edit form view.
     *
     * This method sets the sub-scene to "admin/bookPage.fxml",
     * effectively updating the current view to the edit form.
     */
    @FXML
    private void returnToEdit() {
        SceneManager.getInstance().setSubScene("admin/bookPage.fxml");
    }

    /**
     * Handles the action of saving a form that contains book details.
     *
     * This method captures the information entered in the form fields,
     * creates or updates a Book object, and then either adds it to the
     * repository or updates the existing entry. If `createMethod` is true,
     * the book will be added; otherwise, it will be updated in the repository.
     * Finally, it navigates back to the edit form view.
     */
    @FXML
    private void handleSaveForm() {
//        if (invalidField()) { errorWarning.setText("Please fill out this form."); return; }

        Book book = new Book();

        book.setTitle(titleField.getText());
        book.setAuthors(authorsField.getText());
        book.setIsbn13(isbn13Field.getText());
        book.setDescription(descriptionField.getText());
        book.setCategories(categoriesField.getText());

        if (createMethod) {
            service.add(book);
        }
        else {
            book.setDocumentId(Mediator.book.getDocumentId());
            service.update(book);
        }

        returnToEdit();
    }
}
