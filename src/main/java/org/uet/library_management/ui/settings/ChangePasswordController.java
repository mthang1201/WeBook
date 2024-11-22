package org.uet.library_management.ui.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import lombok.SneakyThrows;
import org.mindrot.jbcrypt.BCrypt;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

import java.lang.reflect.Field;

/**
 * The ChangePasswordController class handles the logic for changing a user's password within the application.
 * It interacts with the UI elements and services to facilitate the password change process.
 */
public class ChangePasswordController {
    @FXML
    public PasswordField newPasswordField;
    public Label emptyLabel;

    /**
     * Handles the action event triggered by clicking the back button.
     * This method changes the current scene to the settings scene, loading the FXML layout from the provided file path.
     */
    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }

    /**
     * Handles the action event triggered by clicking the change password button.
     * This method performs the following actions:
     * 1. Retrieves the new password entered by the user from the UI.
     * 2. Hashes the new password using BCrypt.
     * 3. Updates the password hash for the current user in the session.
     * 4. Invokes the UserService to update the user's password in the repository.
     * 5. Displays a warning alert informing the user that the password has
     *    been successfully changed.
     * 6. Changes the current scene to the settings scene, loading the
     *    FXML layout from "settings/settings.fxml".
     */
    @SneakyThrows
    @FXML
    public void handleChangePasswordButton() {
        String newPassword = newPasswordField.getText();

        if (newPassword.isEmpty()) {
            emptyLabel.setStyle("-fx-text-fill: red");
            return;
        }

        String passwordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        SessionManager.user.setPasswordHash(passwordHash);

        UserService service = new UserService();
        service.update(SessionManager.user);

        AlertUtil.showWarningAlert(
                "Change password",
                "You have changed your password successfully.",
                null,
                null
        );

        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }
}
