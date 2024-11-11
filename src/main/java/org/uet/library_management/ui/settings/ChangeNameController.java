package org.uet.library_management.ui.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

public class ChangeNameController {
    public TextField nameField;

    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }

    @SneakyThrows
    @FXML
    public void handleChangeName() {
        String newName = nameField.getText();
        SessionManager.user.setName(newName);

        UserService service = new UserService();
        service.update(SessionManager.user);

        SessionManager.currentName.set(newName);

        AlertUtil.showWarningAlert(
                "Change name",
                "You have changed your name successfully.",
                null,
                null
        );

        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }
}
