package org.uet.library_management.ui.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

/**
 * The ChangeNameController class handles the user interface and logic for changing a user's name.
 * It interacts with various other components such as SceneManager, SessionManager, and UserService
 * to achieve its functionalities.
 */
public class ChangeNameController {
    public TextField nameField;

    /**
     * Handles the action of the back button. When invoked, this method switches the current scene
     * to the settings scene by loading the "settings/settings.fxml" file.
     *
     * Uses the SceneManager singleton to change the scene.
     */
    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }

    /**
     * Handles the change of the user's name.
     *
     * This method performs the following steps:
     * 1. Retrieves the new name from the name field.
     * 2. Updates the name in the SessionManager's user object.
     * 3. Persists the updated user object using the UserService.
     * 4. Updates the currentName property in the SessionManager.
     * 5. Displays a warning alert stating the name change was successful.
     * 6. Switches the scene to the settings scene using the SceneManager.
     */
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
