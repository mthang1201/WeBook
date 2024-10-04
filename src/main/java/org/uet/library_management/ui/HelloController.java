package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.uet.library_management.entities.Loan;
import org.uet.library_management.entities.User;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.entities.documents.Document;
import org.uet.library_management.entities.documents.Thesis;
import org.uet.library_management.services.LoanService;
import org.uet.library_management.services.UserService;
import org.uet.library_management.services.api.BooksApiService;
import org.uet.library_management.services.documents.BookService;
import org.uet.library_management.services.documents.ThesisService;

import java.io.IOException;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
//        UserService userService = new UserService();
//        List<User> users = userService.findAll();
//        for (User user : users) {
//            System.out.println(String.format("Welcome to %s!", user.getName()));
//        }
//        BookService bookService = new BookService();
//        List<Document> books = bookService.findAll();
//        for (Document book : books) {
//            System.out.println(book.getTitle());
//        }
//        ThesisService thesisService = new ThesisService();
//        List<Document> thesis = thesisService.findAll();
//        for (Document book : thesis) {
//            System.out.println(book.getTitle());
//        }
//        LoanService loanService = new LoanService();
//        List<Loan> loans = loanService.findAll();
//        for (Loan loan : loans) {
//            System.out.println(loan.getLoanId() + " " + loan.getLoanDate() + " " + loan.getDueDate());
//        }
        BooksApiService apiService = new BooksApiService();
        apiService.searchByISBN("0156012197");
    }
}