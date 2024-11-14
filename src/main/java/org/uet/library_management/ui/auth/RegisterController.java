package org.uet.library_management.ui.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterController {
    @FXML
    public Button registerButton;

    @FXML
    public TextField nameField;

    @FXML
    public TextField phoneNumberField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField addressField;

    @FXML
    public ChoiceBox<String> membershipStatusBox;

    @FXML
    public ChoiceBox<String> privilegesBox;

    @FXML
    public PasswordField passwordField;

    @FXML
    public void initialize() {
        membershipStatusBox.getSelectionModel().selectFirst();
        privilegesBox.getSelectionModel().selectFirst();
    }

    @SneakyThrows
    @FXML
    public void handleRegisterButton() {
        String name = nameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String membershipStatus = membershipStatusBox.getValue();
        String privileges = privilegesBox.getValue();
        String password = passwordField.getText();

        if (!validate(name, phoneNumber, email, address, password)) {
            System.out.println("Invalid input.");
            return;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User(name, phoneNumber, email, address, membershipStatus, privileges, passwordHash);

        UserService userService = new UserService();
        userService.add(newUser);

        SceneManager.getInstance().setScene("auth/login.fxml");
    }

    private boolean validate(String name, String phoneNumber, String email, String address, String password) {
        return true;
    }

    @SneakyThrows
    @FXML
    public void handleCancelButton() {
        SceneManager.getInstance().setScene("auth/login.fxml");
    }
}
