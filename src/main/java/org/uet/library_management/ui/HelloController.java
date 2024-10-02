package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.uet.library_management.entities.User;
import org.uet.library_management.services.UserService;

import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        UserService userService = new UserService();
        List<User> users = userService.findAll();
        for (User user : users) {
            System.out.println(String.format("Welcome to %s!", user.getName()));
        }
    }
}