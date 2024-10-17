package org.uet.library_management.ui.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;

public class RegisterController {
    @FXML
    public Button registerButton;

    @SneakyThrows
    @FXML
    public void handleRegisterButton() {
        SceneManager.getInstance().setScene("auth/login.fxml");
    }
}
