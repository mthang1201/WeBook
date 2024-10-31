package org.uet.library_management.ui.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class LoginController {
    @FXML
    public Button loginButton;

    @FXML
    public Button registerButton;

    @FXML
    public TextField emailField;

    @FXML
    public PasswordField passwordField;

    @SneakyThrows
    @FXML
    public void handleLoginButton() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!validate(email, password)) {
            System.out.println("Email or password are not valid.");
            return;
        }

        UserService userService = new UserService();
        Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            System.out.println("User not found.");
            return;
        }

        String storedPasswordHash = user.get().getPasswordHash();

        if (!BCrypt.checkpw(password, storedPasswordHash)) {
            System.out.println("Invalid credentials. Please try again.");
            return;
        }

        if (user.get().getPrivileges().equals("Admin")) {
            SceneManager.getInstance().setScene("admin/admin.fxml");
        } else {
            SceneManager.getInstance().setScene("main.fxml");
        }
    }

    @SneakyThrows
    @FXML
    public void handleRegisterButton() {
        SceneManager.getInstance().setScene("auth/register.fxml");
    }

    private boolean validate(String email, String password) {
        return true;
    }
}
