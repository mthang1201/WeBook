package org.uet.library_management.ui.admin;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.uet.library_management.SceneManager;

/**
 * AdminController is responsible for initializing the admin dashboard.
 * It sets the main content pane and loads the initial sub-scene for the admin interface.
 */
public class AdminController {
    @FXML
    private BorderPane contentPane;

    @FXML
    private void initialize() {
        SceneManager.getInstance().setContentPane(contentPane);
        SceneManager.getInstance().setSubScene("admin/dashboard.fxml");
    }
}

