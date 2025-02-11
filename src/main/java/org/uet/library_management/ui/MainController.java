package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.uet.library_management.SceneManager;

/**
 * The MainController class serves as the main controller for the JavaFX application.
 * It initializes the main content pane and sets the initial sub-scene to "home.fxml".
 *
 * This class is tied to the FXML file describing the layout of the application's main window.
 */
public class MainController {
    @FXML
    private BorderPane contentPane;

    /**
     * Initializes the main content pane of the application and sets the initial sub-scene to "home.fxml".
     * This method is automatically called after the FXML components have been loaded.
     */
    @FXML
    private void initialize() {
        SceneManager.getInstance().setContentPane(contentPane);
        SceneManager.getInstance().pushSubScene("home.fxml");
    }
}

