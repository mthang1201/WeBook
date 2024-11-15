package org.uet.library_management.ui.settings;

import javafx.fxml.FXML;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;

/**
 * Controller class that handles user interactions within the notifications view.
 */
public class NotificationsController {
    /**
     * Handles the action triggered by the back button in the notifications view.
     * This method switches the current scene to the settings scene
     * using the SceneManager's {@code setSettingsScene} method.
     */
    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }
}
