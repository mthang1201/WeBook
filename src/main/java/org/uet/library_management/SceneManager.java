package org.uet.library_management;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage stage;

    private static SceneManager instance;

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        SceneManager.stage = stage;
    }

    public void setScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneName));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
