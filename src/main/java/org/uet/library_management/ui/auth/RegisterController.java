package org.uet.library_management.ui.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.uet.library_management.tools.FontManager;
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

    @FXML
    public ImageView nameIcon;
    public ImageView phoneIcon;
    public ImageView locationIcon;
    public ImageView emailIcon;
    public ImageView passwordIcon;

    @FXML
    public Label emptyLabel;


    @FXML
    public void initialize() {
        //membershipStatusBox.getSelectionModel().selectFirst();
        //privilegesBox.getSelectionModel().selectFirst();
        FontManager.loadFont("Nunito.ttf", 16);
        FontManager.loadFont("Nunito-Black.ttf", 20);
        FontManager.loadFont("Nunito-Medium.ttf", 16);

        nameIcon.setImage(ImageLoaderUtil.getImage("name.png"));
        phoneIcon.setImage(ImageLoaderUtil.getImage("phone.png"));
        locationIcon.setImage(ImageLoaderUtil.getImage("location.png"));
        emailIcon.setImage(ImageLoaderUtil.getImage("email.png"));
        passwordIcon.setImage(ImageLoaderUtil.getImage("password.png"));
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

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty()) {
            emptyLabel.setStyle("-fx-text-fill: red");
            return;
        }

        if (!validateEmail(email)) {
            emptyLabel.setText("Email không hợp lệ!");
            emptyLabel.setStyle("-fx-text-fill: red");
            return;
        }

        if (!validateName(name)) {
            emptyLabel.setText("Tên không hợp lệ!");
            emptyLabel.setStyle("-fx-text-fill: red");
            return;
        }

        if (!validatePhoneNumber(phoneNumber)) {
            emptyLabel.setText("Số điện thoại không hợp lệ!");
            emptyLabel.setStyle("-fx-text-fill: red");
            return;
        }

        if (!validateAddress(address)) {
            emptyLabel.setText("Địa chỉ không hợp lệ!");
            emptyLabel.setStyle("-fx-text-fill: red");
            return;
        }

        if (!validatePassword(password)) {
            emptyLabel.setText("Mật khẩu không hợp lệ! Mật khẩu phải chứa ít nhất 1 chữ cái in hoa, 1 chỡ số và 1 ký tự đặc biệt.");
            emptyLabel.setStyle("-fx-text-fill: red");
            return;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User(name, phoneNumber, email, address, membershipStatus, privileges, passwordHash);

        UserService userService = new UserService();
        userService.add(newUser);

        SceneManager.getInstance().setScene("auth/login.fxml");
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail.com";
        Pattern emailPattern = Pattern.compile(emailRegex);

        if (!emailPattern.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    private boolean validateName(String name) {
        String nameRegex = "^[a-zA-Z\s]+$";
        Pattern namePattern = Pattern.compile(nameRegex);

        if (!namePattern.matcher(name).matches()) {
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "^[0-9]{10}$";
        Pattern phoneNumberPattern = Pattern.compile(phoneNumberRegex);

        if (!phoneNumberPattern.matcher(phoneNumber).matches()) {
            return false;
        }
        return true;
    }

    private boolean validateAddress(String address) {
        String addressRegex = "^[a-zA-Z0-9\\s,.'-]+$";
        Pattern addressPattern = Pattern.compile(addressRegex);

        if (!addressPattern.matcher(address).matches()) {
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).+$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);

        if (!passwordPattern.matcher(password).matches()) {
            return false;
        }
        return true;
    }
    @SneakyThrows
    @FXML
    public void handleCancelButton() {
        SceneManager.getInstance().setScene("auth/login.fxml");
    }
}
