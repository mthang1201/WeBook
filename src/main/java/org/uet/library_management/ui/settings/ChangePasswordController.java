package org.uet.library_management.ui.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import lombok.SneakyThrows;
import org.mindrot.jbcrypt.BCrypt;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.SessionManager;

public class ChangePasswordController {
    @FXML public PasswordField newPasswordField;

    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }

    public void handleChangePasswordButton() {
        String newPassword = newPasswordField.getText();
        String passwordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        UserService userService = new UserService();
        userService.changePassword(passwordHash, SessionManager.user.getUserId());
    }
}
