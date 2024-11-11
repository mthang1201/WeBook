package org.uet.library_management.ui.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

public class ChangeEmailController {
    public TextField emailField;

    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }

    @SneakyThrows
    @FXML
    public void handleChangeEmail() {
        String newEmail = emailField.getText();
        SessionManager.user.setEmail(newEmail);

        UserService service = new UserService();
        service.update(SessionManager.user);

        SessionManager.currentEmail.set(newEmail);

        AlertUtil.showWarningAlert(
                "Change email",
                "You have changed your email successfully.",
                null,
                null
        );

        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }
}
