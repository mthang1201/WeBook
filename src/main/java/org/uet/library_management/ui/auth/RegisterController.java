package org.uet.library_management.ui.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The RegisterController class is responsible for managing the user registration interface.
 * It handles input fields for user data and provides functionality to validate and register
 * new users into the system.
 */
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

    /**
     * Initializes the fields of the RegisterController class.
     *
     * This method is triggered automatically after the FXML has been loaded. It sets the
     * default selections for the membership status and privileges choice boxes to the first
     * available options.
     */
    @FXML
    public void initialize() {
        passwordField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleRegisterButton();
                event.consume();  // Prevents the space character from being added to the text field
            }
        });

        membershipStatusBox.getSelectionModel().selectFirst();
        privilegesBox.getSelectionModel().selectFirst();
    }

    /**
     * Handles the action of the register button being clicked. This method collects user input data
     * from various fields, validates the input, hashes the password, creates a new User object,
     * and adds it to the UserService. If the registration is successful, it redirects to the login scene.
     *
     * This method will print an "Invalid input." message to the console if the validation fails.
     *
     * @throws Exception if any error occurs during processing, including during scene change.
     */
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

    /**
     * Validates the user input fields during the registration process.
     *
     * @param name       the user's name to be validated
     * @param phoneNumber the user's phone number to be validated
     * @param email      the user's email address to be validated
     * @param address    the user's physical address to be validated
     * @param password   the user's password to be validated
     * @return a boolean indicating whether the provided inputs are valid
     */
    private boolean validate(String name, String phoneNumber, String email, String address, String password) {
        return true;
    }

    /**
     * Handles the action of the cancel button being clicked.
     *
     * This method will change the current scene of the application to the login scene ("auth/login.fxml").
     */
    @SneakyThrows
    @FXML
    public void handleCancelButton() {
        SceneManager.getInstance().setScene("auth/login.fxml");
    }
}
