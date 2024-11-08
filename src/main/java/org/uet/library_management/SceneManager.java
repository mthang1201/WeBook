package org.uet.library_management;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class SceneManager {
    private static final int SCREEN_WIDTH = 1000;

    private static final int SCREEN_HEIGHT = 530;

    private static final int SETTINGS_WIDTH = 450;

    private static final int SETTINGS_HEIGHT = 450;

    private static final String PREFIX_URL = "ui/";

    @FXML
    private BorderPane contentPane;

    private static Stage stage;

    private static SceneManager instance;

    private static Stage settingsStage;

    private Stack<String> subSceneStack = new Stack<>();

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

        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("styles/auth.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/menu.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/search.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/bookDetail.css").toExternalForm());

        stage.setTitle("Library Management");
        stage.setScene(scene);
        stage.show();
    }

    public void pushSubScene(String sceneName) {
        subSceneStack.push(sceneName);
        setSubScene(sceneName);
    }

    public void popSubScene() {
        if (!subSceneStack.isEmpty()) {
            subSceneStack.pop();
            if (!subSceneStack.isEmpty()) {
                String sceneName = subSceneStack.peek();
                subSceneStack.clear();
                setSubScene(sceneName);
            }
        }
    }

    public void clearStack() {
        subSceneStack.clear();
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

    public void showNewWindow(String sceneName) throws IOException {
        settingsStage = new Stage();
        setSettingsScene(sceneName);
    }

    public void setSettingsScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PREFIX_URL + sceneName));

        Scene scene = new Scene(fxmlLoader.load(), SETTINGS_WIDTH, SETTINGS_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/settings.css").toExternalForm());

        settingsStage.setTitle("Settings");
        settingsStage.setScene(scene);
        settingsStage.show();
    }
}
