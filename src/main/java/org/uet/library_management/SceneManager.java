package org.uet.library_management;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

/**
 * Singleton class responsible for managing scenes and stages within the application.
 * Provides methods to switch between different scenes and manage sub-scenes and settings windows.
 */
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

    /**
     * Changes the current scene of the application to the specified scene.
     *
     * @param sceneName the name of the FXML file representing the new scene
     * @throws IOException if the FXML file cannot be loaded
     */
    public void setScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PREFIX_URL + sceneName));

        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("styles/login.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/register.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/menu.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/search.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/bookDetail.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("styles/home.css").toExternalForm());

        stage.setTitle("Library Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Pushes a sub-scene onto the sub-scene stack and sets it as the current sub-scene.
     * This is useful for maintaining a history of sub-scenes that can be navigated back and forth.
     *
     * @param sceneName the name of the sub-scene to be pushed onto the stack and set as the current sub-scene
     */
    public void pushSubScene(String sceneName) {
        subSceneStack.push(sceneName);
        setSubScene(sceneName);
    }

    public void popSubScene() {
        if (!subSceneStack.isEmpty()) {
            subSceneStack.pop();
            if (!subSceneStack.isEmpty()) {
                String sceneName = subSceneStack.peek();
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
        if (settingsStage == null) {
            settingsStage = new Stage();
            settingsStage.setResizable(false);
        }
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

    public void closeSettingsWindows() {
        settingsStage.close();
    }
}

