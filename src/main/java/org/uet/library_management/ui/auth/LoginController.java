package org.uet.library_management.ui.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.uet.library_management.notifications.Notification;
import org.uet.library_management.tools.FontManager;
import org.uet.library_management.tools.ImageLoaderUtil;
import org.uet.library_management.tools.SessionManager;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    @FXML
    public Label titleLoginLabel;

    @FXML
    public ImageView emailIcon;

    @FXML
    public ImageView passwordIcon;

    @FXML
    public Button loginButton;

    @FXML
    public Button openRegisterButton;

    @FXML
    public TextField emailLoginField;

    @FXML
    public PasswordField passwordLoginField;

    @SneakyThrows
    @FXML
    public void initialize() {
        FontManager.applyFontToLabel(titleLoginLabel, "Kinetika-Black.ttf", 16);
        FontManager.applyFontToTextField(emailLoginField, "Kinetika-Medium.ttf", 16);
        FontManager.applyFontToPasswordField(passwordLoginField, "Kinetika-Medium.ttf", 16);
        emailIcon.setImage(ImageLoaderUtil.getImage("email.png"));
        passwordIcon.setImage(ImageLoaderUtil.getImage("password.png"));
    }
    @FXML
    public void handleLoginButton() {
        try {
            String email = emailLoginField.getText();
            String password = passwordLoginField.getText();

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

            SessionManager.user = user.get();

            if (user.get().getPrivileges().equals("Admin")) {
                SceneManager.getInstance().setScene("admin/admin.fxml");
            } else {
                SceneManager.getInstance().setScene("main.fxml");
            }

            Notification.checkDueDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOpenRegisterButton() {
        try {
            SceneManager.getInstance().setScene("auth/register.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validate(String email, String password) {
        return true;
    }

}
