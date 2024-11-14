package org.uet.library_management.ui.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

/**
 * The ChangeEmailController class is responsible for handling the changes to a user's email address
 * within the application. This includes updating the email in the session, notifying the user of the change,
 * and navigating back to the settings scene.
 */
public class ChangeEmailController {
    public TextField emailField;

    /**
     * Handles the action when the back button is pressed.
     * Navigates the user back to the settings scene by loading the settings FXML file.
     * This method is annotated with @SneakyThrows to handle any exceptions that may occur
     * during the FXML loading process.
     */
    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }

    /**
     * Handles the action of changing the user's email address.
     * This method interacts with the user interface to retrieve the new email value,
     * updates the user information in the current session, and persists the changes
     * to the user data through the UserService. It also updates the UI to reflect
     * the change and navigates back to the settings scene.
     *
     * The method performs the following steps:
     * - Retrieves the new email address from the email input field.
     * - Updates the email address in the current user session.
     * - Calls the UserService to update the user details in the repository.
     * - Updates the current email property in the session manager.
     * - Displays a success alert to notify the user that the email has been changed.
     * - Navigates back to the settings scene.
     *
     * This method is annotated with @SneakyThrows to handle any exceptions
     * that may occur during the execution, particularly when navigating to
     * the settings scene.
     */
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
