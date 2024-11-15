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
    @FXML public Button bookshelfButton;
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

        FontManager.applyFontToButton(getAllButton, "Nunito Regular.ttf", 16);
        FontManager.applyFontToButton(bookmarkButton, "Nunito Regular.ttf", 16);
        FontManager.applyFontToLabel(menuLabel, "Nunuto Bold", 16);

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

        bookshelfButton.setOnMouseEntered(event ->
                handleButtonHover("bookshelf-white", bookshelfImageView)
        );
        bookshelfButton.setOnMouseExited(event ->
                handleButtonHover("bookshelf", bookshelfImageView)
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

    private void handleButtonHover(String imageName, ImageView imageView) {
        imageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + imageName + ".png")));
    }

    private void handleSearchTextFieldMouseClick(MouseEvent event) {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("search/search.fxml");
    }

    @FXML
    private void handleHomeMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("home.fxml");
    }

    @FXML
    private void handleBookshelfMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("bookshelf.fxml");
    }

    @FXML
    private void handleGetAllMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("getAll.fxml");
    }

    @FXML
    private void handleBookmarkMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("bookmark.fxml");
    }

    @FXML
    private void handleFinishedMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("finished.fxml");
    }

    @FXML
    private void handleAddBooksMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("addBooks.fxml");
    }

    @SneakyThrows
    @FXML
    private void handleSettingsMenu() {
        SceneManager.getInstance().showNewWindow("settings/settings.fxml");
    }
}
