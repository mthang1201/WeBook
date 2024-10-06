package org.uet.library_management;

import javafx.application.Application;
import javafx.stage.Stage;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.services.api.search.SearchByISBN;
import org.uet.library_management.services.api.search.SearchByTitle;
import org.uet.library_management.services.api.search.SearchContext;

import java.io.IOException;
import java.util.List;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().setStage(stage);
        SceneManager.getInstance().setScene("main.fxml");
    }

    public static void main(String[] args) {

        /* Test
        SearchContext test = new SearchContext();
        test.setStrategy(new SearchByISBN());
        List<Book> searchTest1 = test.executeSearch("0156012197");
        test.setStrategy(new SearchByTitle());
        List<Book> searchTest2 = test.executeSearch("oop");
        for (Book book : searchTest1) {
            System.out.println(book.getTitle());
        }
        for (Book book : searchTest2) {
            System.out.println(book.getTitle());
        }
        */
        launch();
    }
}