package org.uet.library_management.ui.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.uet.library_management.SceneManager;

public class AdminMenuController {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    @FXML
    public TextField searchTextField;
    @FXML public Button homeButton;
    @FXML public Button userButton;
    @FXML public Button loanButton;
    @FXML public Button editButton;
    @FXML public Button usernameButton;

    @FXML public ImageView homeImageView;
    @FXML public ImageView userImageView;
    @FXML public ImageView loanImageView;
    @FXML public ImageView editImageView;
    @FXML public ImageView usernameImageView;

    @FXML
    public void initialize() {
        // Set mouse event handlers
        homeButton.setOnMouseEntered(event ->
                handleButtonHover("home-white", homeImageView)
        );
        homeButton.setOnMouseExited(event ->
                handleButtonHover("home", homeImageView)
        );

        userButton.setOnMouseEntered(event ->
                handleButtonHover("books-white", userImageView)
        );
        userButton.setOnMouseExited(event ->
                handleButtonHover("books", userImageView)
        );

        loanButton.setOnMouseEntered(event ->
                handleButtonHover("upload-white", loanImageView)
        );
        loanButton.setOnMouseExited(event ->
                handleButtonHover("upload", loanImageView)
        );

        editButton.setOnMouseEntered(event ->
                handleButtonHover("edit-white", editImageView)
        );
        editButton.setOnMouseExited(event ->
                handleButtonHover("edit", editImageView)
        );
    }

    private void handleButtonHover(String imageName, ImageView imageView) {
        imageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + imageName + ".png")));
    }

    @FXML
    private void handleHomeMenu() {
        SceneManager.getInstance().setSubScene("admin/dashboard.fxml");
    }

    @FXML
    public void handleUserMenu(ActionEvent actionEvent) {
        SceneManager.getInstance().setSubScene("admin/userPage.fxml");
    }

    @FXML
    public void handleLoanMenu(ActionEvent actionEvent) {
        SceneManager.getInstance().setSubScene("admin/loanPage.fxml");
    }

    @FXML
    private void handleEditMenu() {
        SceneManager.getInstance().setSubScene("edit.fxml");
    }
}
