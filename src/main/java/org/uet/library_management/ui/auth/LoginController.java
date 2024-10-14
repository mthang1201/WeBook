package org.uet.library_management.ui.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;

public class LoginController {
    @FXML
    public Button loginButton;

    @SneakyThrows
    @FXML
    public void handleLoginButton() {
        SceneManager.getInstance().setScene("main.fxml");
    }
}
