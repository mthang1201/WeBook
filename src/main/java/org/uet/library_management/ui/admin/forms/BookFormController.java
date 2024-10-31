package org.uet.library_management.ui.admin.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.Mediator;

public class BookFormController {
    private BookService service;

    @FXML private TextField titleField;
    @FXML private TextField authorsField;
    @FXML private TextField isbn13Field;
    @FXML private TextField descriptionField;
    @FXML private TextField categoriesField;

    @FXML private Label errorWarning;

    private boolean createMethod = true;

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

    private boolean invalidField() {
        if (titleField.getText() == null || titleField.getText().trim().equals("")) { return true; }
        if (authorsField.getText() == null || authorsField.getText().trim().equals("")) { return true; }
        if (isbn13Field.getText() == null || isbn13Field.getText().trim().equals("")) { return true; }
        if (descriptionField.getText() == null || descriptionField.getText().trim().equals("")) { return true; }
        if (categoriesField.getText() == null || categoriesField.getText().trim().equals("")) { return true; }
        return false;
    }

    @FXML
    private void returnToEdit() {
        SceneManager.getInstance().setSubScene("admin/edit.fxml");
    }

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
