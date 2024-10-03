package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.uet.library_management.entities.User;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.entities.documents.Document;
import org.uet.library_management.entities.documents.Thesis;
import org.uet.library_management.services.UserService;
import org.uet.library_management.services.documents.BookService;
import org.uet.library_management.services.documents.ThesisService;

import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        UserService userService = new UserService();
        List<User> users = userService.findAll();
        for (User user : users) {
            System.out.println(String.format("Welcome to %s!", user.getName()));
        }
        BookService bookService = new BookService();
        List<Document> books = bookService.findAll();
        for (Document book : books) {
            System.out.println(book.getTitle());
        }
        ThesisService thesisService = new ThesisService();
        List<Document> thesis = thesisService.findAll();
        for (Document book : thesis) {
            System.out.println(book.getTitle());
        }
    }
}