package org.uet.library_management;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static final String PREFIX_URL = "ui/";

    @FXML
    private BorderPane contentPane;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PREFIX_URL + sceneName));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 530);
        scene.getStylesheets().add(getClass().getResource("styles/auth.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/menu.css").toExternalForm());

        stage.setTitle("Library Management");
        stage.setScene(scene);
        stage.show();
    }

    public void setContentPane(BorderPane contentPane) {
        this.contentPane = contentPane;
    }

    public void setSubScene(String sceneName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PREFIX_URL + sceneName));
            BorderPane pageContent = loader.load();
            contentPane.setCenter(pageContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
