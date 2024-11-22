package org.uet.library_management.ui.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.repositories.UserAvatarRepository;
import org.uet.library_management.tools.FontManager;
import org.uet.library_management.tools.SessionManager;

/**
 * SettingsController is responsible for managing user settings in the application.
 * It handles initialization of user information and various user-related actions like
 * updating user details, handling notifications, and signing out.
 */
public class SettingsController {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    public ImageView avatar;
    public Label userName;
    public Label userEmail;

    /**
     * Initializes the settings user interface with the current user's details or sets default values
     * for `Anonymous` user if no user is currently logged in. It sets the user's name, email, and avatar.
     * Also attaches listeners to update the UI in response to changes in the user's session properties.
     *
     * If the user is not logged in, default values for name, email, and avatar are set.
     * If the user is logged in, values from the user's session are used.
     * Additionally, listeners are set up to respond to changes in the session's currentName, currentEmail,
     * and currentAvatar properties.
     *
     * The avatar image is retrieved from the `UserAvatarRepository`. If no image is found in the repository,
     * a default avatar image is used.
     */
    @FXML
    public void initialize() {
        FontManager.loadFont("Nunito.ttf", 20);
        FontManager.loadFont("Nunito-Black.ttf", 20);

        if (SessionManager.user == null) {
            userName.setText("Anonymous");
            userEmail.setText("abc123@gmail.com");

            avatar.setImage(
                    new Image(
                            getClass().getResourceAsStream(PREFIX_ICONS + "avatar-male.png")
                    )
            );
        } else {
            userName.setText(SessionManager.user.getName());
            userEmail.setText(SessionManager.user.getEmail());

            UserAvatarRepository repository = new UserAvatarRepository();
            Image image;
            image = repository.findByUserId(SessionManager.user.getUserId());

            if (image == null) {
                image = new Image(
                        getClass().getResourceAsStream(PREFIX_ICONS + "avatar-male.png")
                );
            }

            avatar.setImage(image);

            SessionManager.currentName.addListener((observable, oldText, newText) -> {
                if (newText != null) {
                    userName.setText(newText);
                }
            });

            SessionManager.currentEmail.addListener((observable, oldText, newText) -> {
                if (newText != null) {
                    userEmail.setText(newText);
                }
            });

            SessionManager.currentAvatar.addListener((observable, oldImage, newImage) -> {
                if (newImage != null) {
                    avatar.setImage(newImage);
                }
            });
        }
    }

    /**
     * Handles the ActionEvent triggered by the notifications button or menu item.
     * This method switches the current scene to the notifications settings scene by loading
     * the "settings/notifications.fxml" file. It utilizes the SceneManager singleton to manage
     * the scene transition.
     */
    @SneakyThrows
    @FXML
    public void handleNotifications() {
        SceneManager.getInstance().setSettingsScene("settings/notifications.fxml");
    }

    /**
     * Handles the user sign-out process by performing the following actions:
     *
     * 1. Sets the current user session to null in the SessionManager.
     * 2. Closes any open settings windows using the SceneManager.
     * 3. Changes the current scene to the login page.
     *
     * This method is linked to a user action event and ensures the application
     * is reset to a logged-out state, ready for a new user login.
     */
    @SneakyThrows
    @FXML
    private void handleSignOut() {
        SessionManager.user = null;
        SceneManager.getInstance().closeSettingsWindows();
        SceneManager.getInstance().setScene("auth/login.fxml");
    }

    /**
     * Handles the action event triggered by the avatar button.
     * This method switches the current scene to the avatar change settings scene
     * by loading the "settings/changeAvatar.fxml" file.
     * It utilizes the SceneManager singleton to manage the scene transition.
     */
    @SneakyThrows
    @FXML
    public void handleAvatarButton() {
        SceneManager.getInstance().setSettingsScene("settings/changeAvatar.fxml");
    }

    /**
     * Handles the action event triggered by the name button.
     * This method switches the current scene to the name change settings scene
     * by loading the "settings/changeName.fxml" file.
     * It utilizes the SceneManager singleton to manage the scene transition.
     */
    @SneakyThrows
    @FXML
    public void handleNameButton() {
        SceneManager.getInstance().setSettingsScene("settings/changeName.fxml");
    }

    /**
     * Handles the action event triggered by the password button.
     * This method switches the current scene to the password change settings scene
     * by loading the "settings/changePassword.fxml" file.
     * It utilizes the SceneManager singleton to manage the scene transition.
     */
    @SneakyThrows
    @FXML
    public void handlePassword() {
        SceneManager.getInstance().setSettingsScene("settings/changePassword.fxml");
    }

    /**
     * Handles the action event triggered by the "About" button or menu item.
     * This method switches the current scene to the "About" settings scene by loading
     * the "settings/about.fxml" file. It utilizes the SceneManager singleton to manage
     * the scene transition.
     */
    @SneakyThrows
    @FXML
    public void handleAbout() {
        SceneManager.getInstance().setSettingsScene("settings/about.fxml");
    }

    /**
     * Handles the action event triggered by the email button.
     * This method switches the current scene to the email change settings scene
     * by loading the "settings/changeEmail.fxml" file.
     * It utilizes the SceneManager singleton to manage the scene transition.
     */
    @SneakyThrows
    @FXML
    public void handleEmailButton() {
        SceneManager.getInstance().setSettingsScene("settings/changeEmail.fxml");
    }
}
