package org.uet.library_management.ui.settings;

import javafx.fxml.FXML;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;

public class NotificationsController {
    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }
}
