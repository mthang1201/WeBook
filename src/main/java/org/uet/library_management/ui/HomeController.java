package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.api.search.SearchByTitle;
import org.uet.library_management.api.search.SearchContext;

import java.util.List;

public class HomeController {
    @FXML
    public FlowPane flowPane;

    @FXML
    public void initialize() {
        flowPane.setPadding(new Insets(10,10,10,10));
        Book book1 = new Book();

        SearchContext test = new SearchContext();
        test.setStrategy(new SearchByTitle());
        List<Book> searchTest2 = test.executeSearch("d");

        for (Book book : searchTest2) {
            VBox vbox = new VBox();
            ImageView imageView = new ImageView(new Image(book.getImageLinks(), true));
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);

            Label titleLabel = new Label(book.getTitle());
            Label authorsLabel = new Label(book.getAuthors());

            vbox.getChildren().addAll(imageView, titleLabel, authorsLabel);
            flowPane.getChildren().add(vbox);
        }
    }
}
