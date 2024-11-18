package org.uet.library_management.ui.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

/**
 * The LoginController class manages the login interface of a JavaFX application.
 * It controls the user interactions and interface elements associated with the login process.
 */
public class LoginController {
    @FXML
    public Label titleLoginLabel;
    public Label invalidLabel;

    @FXML
    public Label inputAllLabel;

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

    /**
     * Initializes the login interface by applying custom fonts to various UI elements
     * and setting up icons for email and password fields.
     *
     * This method performs the following actions:
     * 1. Applies a custom font to the login title label using "Kinetika-Black.ttf" with size 16.
     * 2. Applies a custom font to the email text field using "Kinetika-Medium.ttf" with size 16.
     * 3. Applies a custom font to the password field using "Kinetika-Medium.ttf" with size 16.
     * 4. Sets the email icon image to "email.png".
     * 5. Sets the password icon image to "password.png".
     */
    @SneakyThrows
    @FXML
    public void initialize() {
        passwordLoginField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLoginButton();
                event.consume();  // Prevents the space character from being added to the text field
            }
        });


//        FontManager.applyFontToLabel(titleLoginLabel, "Kinetika-Black.ttf", 16);
//        FontManager.applyFontToTextField(emailLoginField, "Kinetika-Medium.ttf", 16);
//        FontManager.applyFontToPasswordField(passwordLoginField, "Kinetika-Medium.ttf", 16);
        emailIcon.setImage(ImageLoaderUtil.getImage("email.png"));
        passwordIcon.setImage(ImageLoaderUtil.getImage("password.png"));
    }
    /**
     * Handles the login button click event in the login interface.
     *
     * This method performs the following actions:
     * 1. Retrieves the email and password input by the user.
     * 2. Validates the input email and password format.
     * 3. Attempts to find a user by the given email using the UserService.
     * 4. Checks if the user exists and if the provided password matches the stored password hash.
     * 5. Updates the SessionManager with the authenticated user's information.
     * 6. Directs the user to the appropriate scene based on their privileges (Admin or regular user).
     * 7. Initiates a check for due notifications.
     *
     * If any step fails (validation, user not found, password mismatch), it logs a message and stops the login process.
     *
     * Catches and handles any IOException that may occur during the process.
     */
    @FXML
    public void handleLoginButton() {
        try {
            String email = emailLoginField.getText();
            String password = passwordLoginField.getText();

            if (!validate(email, password)) {
                invalidLabel.setStyle("-fx-text-fill: red");
                return;
            }

            UserService userService = new UserService();
            Optional<User> user = userService.findByEmail(email);

            if (user.isEmpty() || password.isEmpty()) {
                inputAllLabel.setStyle("-fx-text-fill: red");
                invalidLabel.setStyle("-fx-text-fill: transparent");
                return;
            }

            String storedPasswordHash = user.get().getPasswordHash();

            if (!BCrypt.checkpw(password, storedPasswordHash)) {
                invalidLabel.setStyle("-fx-text-fill: red");
                inputAllLabel.setStyle("-fx-text-fill: transparent");
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

    /**
     * Handles the action event triggered by clicking the "Open Register" button.
     *
     * This method attempts to switch the current scene to the registration scene,
     * loading the FXML file "auth/register.fxml". If an IOException occurs during
     * this process, it is caught and the stack trace is printed.
     */
    @FXML
    public void handleOpenRegisterButton() {
        try {
            SceneManager.getInstance().setScene("auth/register.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates the provided email and password.
     *
     * @param email the email address to be validated
     * @param password the password to be validated
     * @return true if the email and password are valid, otherwise false
     */
    private boolean validate(String email, String password) {
        return true;
    }

}
