package org.uet.library_management.ui.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.ImageLoaderUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Label emptyLabel;

    @FXML
    public ImageView nameIcon;

    @FXML
    public ImageView phoneIcon;

    @FXML
    public ImageView locationIcon;

    @FXML
    public ImageView emailIcon;

    @FXML
    public ImageView passwordIcon;

    @FXML
    public HBox nameBox;
    public HBox phoneNumberBox;
    public HBox emailBox;
    public HBox addressBox;
    public HBox passwordBox;

    @FXML
    public void initialize() {
        membershipStatusBox.getSelectionModel().selectFirst();
        privilegesBox.getSelectionModel().selectFirst();

        emailIcon.setImage(ImageLoaderUtil.getImage("email.png"));
        passwordIcon.setImage(ImageLoaderUtil.getImage("password.png"));
        nameIcon.setImage(ImageLoaderUtil.getImage("name.png"));
        phoneIcon.setImage(ImageLoaderUtil.getImage("phone.png"));
        locationIcon.setImage(ImageLoaderUtil.getImage("location.png"));
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

/*        if (!validate(name, phoneNumber, email, address, password)) {
            System.out.println("Invalid input.");
            return;
        }*/

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty()) {
            emptyLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User(name, phoneNumber, email, address, membershipStatus, privileges, passwordHash);

        UserService userService = new UserService();
        userService.add(newUser);

        SceneManager.getInstance().setScene("auth/login.fxml");
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

/*    private boolean validateName(String name) {

    }*/

    @SneakyThrows
    @FXML
    public void handleCancelButton() {
        SceneManager.getInstance().setScene("auth/login.fxml");
    }
}
