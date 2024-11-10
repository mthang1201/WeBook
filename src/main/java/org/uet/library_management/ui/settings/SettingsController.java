package org.uet.library_management.ui.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.repositories.UserAvatarRepository;
import org.uet.library_management.tools.SessionManager;

public class SettingsController {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    public ImageView avatar;
    public Label userName;
    public Label userEmail;

    public void initialize() {
        if (SessionManager.user == null) {
            avatar.setImage(
                    new Image(
                            getClass().getResourceAsStream(PREFIX_ICONS + "avatar-male.png")
                    )
            );
        } else {
            UserAvatarRepository repository = new UserAvatarRepository();
            Image image;
            image = repository.findByUserId(SessionManager.user.getUserId());

            if (image == null) {
                image = new Image(
                        getClass().getResourceAsStream(PREFIX_ICONS + "avatar-male.png")
                );
            }

            avatar.setImage(image);
        }

        SessionManager.currentAvatar.addListener((observable, oldImage, newImage) -> {
            if (newImage != null) {
                avatar.setImage(newImage);
            }
        });

        userName.setText(SessionManager.user.getName());
        userEmail.setText(SessionManager.user.getEmail());
    }

    @SneakyThrows
    @FXML
    public void handleNotifications(ActionEvent actionEvent) {
        SceneManager.getInstance().setSettingsScene("settings/notifications.fxml");
    }

    @SneakyThrows
    @FXML
    public void handleAccountSettings() {
        SceneManager.getInstance().setSettingsScene("settings/accountSettings.fxml");
    }

    @SneakyThrows
    @FXML
    private void handleSignOut() {
        SessionManager.user = null;
        SceneManager.getInstance().setScene("auth/login.fxml");
    }

    @SneakyThrows
    @FXML
    public void handleAvatarButton() {
        SceneManager.getInstance().setSettingsScene("settings/changeAvatar.fxml");
    }

    @SneakyThrows
    @FXML
    public void handleNameButton() {
        SceneManager.getInstance().setSettingsScene("settings/changeName.fxml");
    }

    @SneakyThrows
    @FXML
    public void handlePassword() {
        SceneManager.getInstance().setSettingsScene("settings/changePassword.fxml");
    }
}
