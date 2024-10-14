package org.uet.library_management;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().setStage(stage);
        SceneManager.getInstance().setScene("main.fxml");
    }

    public static void main(String[] args) {
//         FuzzyScore fuzzyScore = new FuzzyScore(Locale.ENGLISH);
//         SearchContext test = new SearchContext();
        //test.setStrategy(new SearchByISBN());
        //List<Book> searchTest1 = test.executeSearch("0156012197");
//         test.setStrategy(new SearchByTitle());
//         List<Book> searchTest2 = test.executeSearch("oop");
//         List<Book> result = new ArrayList<>();
//         for (Book book : searchTest2) {
//             int score = fuzzyScore.fuzzyScore(book.getTitle(),"oop" );
//             if (score > 0) {
//                 result.add(book);
//             }
//         }
//         for (Book book : result) {

//         }
//        for (Book book : searchTest2) {
//            System.out.println(book.getTitle());
//        }
//        SearchContext test = new SearchContext();
//        test.setStrategy(new SearchByISBN());
//        test.executeSearch("0156012197");
//        test.setStrategy(new SearchByTitle());
//        test.executeSearch("oop");
        launch();
    }
}