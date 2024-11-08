package org.uet.library_management.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.tools.SessionManager;

public class SettingsController {
    public ImageView avatar;
    public Label userName;
    public Label userEmail;

    public void initialize() {
//        avatar.setImage(new Image("/src/recourses/org/uet/library_management/icons/avatar.png"));
        userName.setText(SessionManager.user.getName());
        userEmail.setText(SessionManager.user.getEmail());
    }

    public void handleAccountSettings() {
    }

    @SneakyThrows
    @FXML
    private void handleSignOut() {
        SessionManager.user = null;
        SceneManager.getInstance().setScene("auth/login.fxml");
    }
}
