package org.uet.library_management.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.repositories.UserAvatarRepository;
import org.uet.library_management.tools.FontManager;
import org.uet.library_management.tools.Mediator;
import org.uet.library_management.tools.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller class for managing the menu actions and search functionality
 * within the library management system's user interface.
 */
public class MenuController {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    @FXML public TextField searchTextField;
    @FXML public Button homeButton;
    @FXML public Button getAllButton;
    @FXML public Button bookmarkButton;
    @FXML public Button addBooksButton;
    @FXML public Button usernameButton;

    @FXML public ImageView homeImageView;
    @FXML public ImageView getAllImageView;
    @FXML public ImageView bookshelfImageView;
    @FXML public ImageView bookmarkImageView;
    @FXML public ImageView addBooksImageView;
    @FXML public ImageView usernameImageView;

    @FXML public Label menuLabel;


    private Timer timer;

    /**
     * Initializes the MenuController.
     * This method sets up various event listeners and handles the UI state
     * based on the current user's session.
     *
     * Key functionalities:
     * - Adds a ChangeListener to the searchTextField to handle text input changes with a debounce effect.
     * - Sets up mouse event handlers for various buttons to change their images on hover.
     * - Updates the UI elements based on the user's session, including the username and avatar image.
     * - Initializes a timer to delay actions on text input change, updating the UI based on the content.
     */
    @FXML
    public void initialize() {

        FontManager.loadFont("Nunito.ttf", 60);
        FontManager.loadFont("Nunito-Bold.ttf", 16);
        FontManager.loadFont("Nunito-Light.ttf", 16);
        FontManager.loadFont("Nunito-Medium.ttf", 16);

        //FontManager.applyFontToLabel(menuLabel, "Nunito.ttf", 16);

        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (timer != null) {
                    timer.cancel();
                }
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            if (!newValue.isEmpty()) {
                                Mediator.text = searchTextField.getText();
                                SceneManager.getInstance().pushSubScene("search/suggestSearch.fxml");
                            } else {
                                SceneManager.getInstance().clearStack();
                                SceneManager.getInstance().pushSubScene("search/search.fxml");
                            }
                        });
                    }
                }, 300);
            }
        });

        // Set mouse event handlers
        searchTextField.setOnMouseClicked(this::handleSearchTextFieldMouseClick);

        homeButton.setOnMouseEntered(event ->
                handleButtonHover("home-white", homeImageView)
        );
        homeButton.setOnMouseExited(event ->
                handleButtonHover("home", homeImageView)
        );

        getAllButton.setOnMouseEntered(event ->
                handleButtonHover("getAll-white", getAllImageView)
        );
        getAllButton.setOnMouseExited(event ->
                handleButtonHover("getAll", getAllImageView)
        );

        bookmarkButton.setOnMouseEntered(event ->
                handleButtonHover("bookmark-white", bookmarkImageView)
        );
        bookmarkButton.setOnMouseExited(event ->
                handleButtonHover("bookmark", bookmarkImageView)
        );

        addBooksButton.setOnMouseEntered(event ->
                handleButtonHover("upload-white", addBooksImageView)
        );
        addBooksButton.setOnMouseExited(event ->
                handleButtonHover("upload", addBooksImageView)
        );

        if (SessionManager.user == null) {
            usernameButton.setText("Anonymous");

            usernameImageView.setImage(
                    new Image(
                            getClass().getResourceAsStream(PREFIX_ICONS + "avatar-male.png")
                    )
            );
        } else {
            usernameButton.setText(SessionManager.user.getName());

            UserAvatarRepository repository = new UserAvatarRepository();
            Image image;
            image = repository.findByUserId(SessionManager.user.getUserId());

            if (image == null) {
                image = new Image(
                        getClass().getResourceAsStream(PREFIX_ICONS + "avatar-male.png")
                );
            }

            usernameImageView.setImage(image);

            SessionManager.currentName.addListener((observable, oldText, newText) -> {
                if (newText != null) {
                    usernameButton.setText(newText);
                }
            });

            SessionManager.currentAvatar.addListener((observable, oldImage, newImage) -> {
                if (newImage != null) {
                    usernameImageView.setImage(newImage);  // Update ImageView when the image changes
                }
            });

        }
        //FontManager.applyFontToLabel(label, "Nunito-Regular.ttf", 16);
    }

    /**
     * Handles the hover effect for a given button by changing its associated image.
     *
     * @param imageName the base name of the image to be displayed, without the extension and path
     * @param imageView the ImageView component whose image will be changed
     */
    private void handleButtonHover(String imageName, ImageView imageView) {
        imageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + imageName + ".png")));
    }

    /**
     * Handles the mouse click event on the search text field and transitions to the search scene.
     *
     * @param event the MouseEvent that triggered this handler
     */
    private void handleSearchTextFieldMouseClick(MouseEvent event) {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("search/search.fxml");
    }

    /**
     * Handles the action for the "Home" menu item.
     * This method clears the current scene stack and sets the scene to "home.fxml".
     */
    @FXML
    private void handleHomeMenu() {
//        if (timer != null) {
//            timer.cancel();
//        }
//
//        timer = new Timer();
//
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
                    SceneManager.getInstance().clearStack();
                    SceneManager.getInstance().pushSubScene("home.fxml");
//                });
//            }
//        }, 300);
    }



    /**
     * Handles the action for the "Get All" menu item.
     *
     * This method performs the following actions:
     * - Clears the current scene stack.
     * - Pushes "borrowedBooks.fxml" onto the sub-scene stack and sets it as the current sub-scene.
     */
    @FXML
    private void handleGetAllMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("borrowedBooks.fxml");
    }

    /**
     * Handles the action for the "Bookmark" menu item.
     *
     * This method performs the following actions:
     * - Clears the current scene stack to ensure a fresh navigation state.
     * - Pushes "bookmark.fxml" onto the sub-scene stack and sets it as the current sub-scene.
     */
    @FXML
    private void handleBookmarkMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("bookmark.fxml");
    }

    /**
     * Handles the action for the "Add Books" menu item.
     * This method performs the following actions:
     * - Clears the current scene stack to ensure a fresh navigation state.
     * - Pushes "addBooks.fxml" onto the sub-scene stack and sets it as the current sub-scene.
     */
    @FXML
    private void handleAddBooksMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("addBooks.fxml");
    }

    /**
     * Handles the action for the "Settings" menu item.
     * This method opens a new window displaying the settings page,
     * specifically loading the "settings/settings.fxml" file to populate the settings window.
     * It ensures that the SceneManager singleton instance is used to manage the settings window.
     */
    @SneakyThrows
    @FXML
    private void handleSettingsMenu() {
        SceneManager.getInstance().showNewWindow("settings/settings.fxml");
    }
}
