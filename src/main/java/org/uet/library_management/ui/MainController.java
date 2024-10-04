package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.uet.library_management.SceneManager;

public class MainController {
    @FXML
    private BorderPane contentPane;

    @FXML
    private void initialize() {
        SceneManager.getInstance().setContentPane(contentPane);
        SceneManager.getInstance().setSubScene("hello-view.fxml");
    }
}

