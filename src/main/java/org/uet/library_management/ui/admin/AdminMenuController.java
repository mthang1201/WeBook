package org.uet.library_management.ui.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.tools.SessionManager;

/**
 * The AdminMenuController class is responsible for managing the admin menu interface in the library
 * management system. It handles user interactions with the menu buttons and manages the display of
 * different user interface components based on these interactions.
 */
public class AdminMenuController {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    @FXML
    public TextField searchTextField;
    @FXML public Button homeButton;
    @FXML public Button userButton;
    @FXML public Button loanButton;
    @FXML public Button editButton;
    @FXML public Button usernameButton;
    @FXML public Button logoutButton;

    @FXML public ImageView homeImageView;
    @FXML public ImageView userImageView;
    @FXML public ImageView loanImageView;
    @FXML public ImageView editImageView;
    @FXML public ImageView usernameImageView;
    @FXML public ImageView logoutImageView;

    /**
     * Initializes the AdminMenuController. This method sets mouse event handlers for various buttons
     * and updates the username button text based on the current session's user.
     *
     * This method performs the following actions:
     * 1. Sets mouse enter and exit event handlers for `homeButton`, `userButton`, `loanButton`, and `editButton`.
     * 2. Changes the image of the associated ImageView upon hover events by calling `handleButtonHover`.
     * 3. Sets the `usernameButton` text to the name of the current user from the session, or defaults to "Test" if the user is null.
     *
     * Specific event handlers:
     * - `homeButton`: Switches between "home-white" and "home" images.
     * - `userButton`: Switches between "books-white" and "books" images.
     * - `loanButton`: Switches between "upload-white" and "upload" images.
     * - `editButton`: Switches between "edit-white" and "edit" images.
     */
    @FXML
    public void initialize() {
        // Set mouse event handlers
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

        if (SessionManager.user != null) {
            usernameButton.setText(SessionManager.user.getName());
        } else {
            usernameButton.setText("Test");
        }
    }

    /**
     * Updates the image of the specified ImageView based on the provided image name.
     *
     * @param imageName the name of the image file (without extension) to be used for the ImageView.
     * @param imageView the ImageView object whose image will be updated.
     */
    private void handleButtonHover(String imageName, ImageView imageView) {
        imageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + imageName + ".png")));
    }

    /**
     * Handles the event when the home menu button is clicked.
     * This method switches the current sub-scene to the admin dashboard page.
     */
    @FXML
    private void handleHomeMenu() {
        SceneManager.getInstance().setSubScene("admin/userPage.fxml");
    }

    /**
     * Handles the event when the user menu button is clicked.
     * This method switches the current sub-scene to the user management page.
     *
     * @param actionEvent the event triggered by clicking the user menu button
     */
    @FXML
    public void handleUserMenu(ActionEvent actionEvent) {
        SceneManager.getInstance().setSubScene("admin/userPage.fxml");
    }

    /**
     * Handles the event when the loan menu button is clicked.
     * This method switches the current sub-scene to the loan management page.
     *
     * @param actionEvent the event triggered by clicking the loan menu button
     */
    @FXML
    public void handleLoanMenu(ActionEvent actionEvent) {
        SceneManager.getInstance().setSubScene("admin/loanPage.fxml");
    }

    /**
     * Handles the event when the edit menu button is clicked.
     * This method switches the current sub-scene to the "admin/edit.fxml" scene.
     */
    @FXML
    private void handleEditMenu() {
        SceneManager.getInstance().setSubScene("admin/edit.fxml");
    }

    /**
     * Handles the event when the logout menu button is clicked.
     * This method switches the current scene to the authentication login page defined in "auth/login.fxml".
     */
    @SneakyThrows
    @FXML
    public void handleLogoutMenu() {
        SceneManager.getInstance().setScene("auth/login.fxml");
    }
}
