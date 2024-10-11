package org.uet.library_management;

import javafx.application.Application;
import javafx.stage.Stage;
import org.uet.library_management.services.api.search.SearchByISBN;
import org.uet.library_management.services.api.search.SearchByTitle;
import org.uet.library_management.services.api.search.SearchContext;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().setStage(stage);
        SceneManager.getInstance().setScene("main.fxml");
    }

    public static void main(String[] args) {

//        SearchContext test = new SearchContext();
//        test.setStrategy(new SearchByISBN());
//        test.executeSearch("0156012197");
//        test.setStrategy(new SearchByTitle());
//        test.executeSearch("oop");
        launch();
    }
}