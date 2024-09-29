package org.uet.library_management;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().setStage(stage);
        SceneManager.getInstance().setScene("hello-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}