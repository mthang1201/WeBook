package org.uet.library_management.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.tools.Mediator;
import org.uet.library_management.tools.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

public class MenuController {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    @FXML public TextField searchTextField;
    @FXML public Button homeButton;
    @FXML public Button bookshelfButton;
    @FXML public Button getAllButton;
    @FXML public Button bookmarkButton;
    @FXML public Button finishedButton;
    @FXML public Button booksButton;
    @FXML public Button addBooksButton;
    @FXML public Button usernameButton;
    @FXML public Button logoutButton;

    @FXML public ImageView homeImageView;
    @FXML public ImageView getAllImageView;
    @FXML public ImageView bookshelfImageView;
    @FXML public ImageView bookmarkImageView;
    @FXML public ImageView finishedImageView;
    @FXML public ImageView booksImageView;
    @FXML public ImageView addBooksImageView;
    @FXML public ImageView usernameImageView;
    @FXML public ImageView logoutImageView;

    private Timer timer;

    @FXML
    public void initialize() {
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

        finishedButton.setOnMouseEntered(event ->
                handleButtonHover("finished-white", finishedImageView)
        );
        finishedButton.setOnMouseExited(event ->
                handleButtonHover("finished", finishedImageView)
        );

        booksButton.setOnMouseEntered(event ->
                handleButtonHover("books-white", booksImageView)
        );
        booksButton.setOnMouseExited(event ->
                handleButtonHover("books", booksImageView)
        );

        addBooksButton.setOnMouseEntered(event ->
                handleButtonHover("upload-white", addBooksImageView)
        );
        addBooksButton.setOnMouseExited(event ->
                handleButtonHover("upload", addBooksImageView)
        );

        if (SessionManager.user != null) {
            usernameButton.setText(SessionManager.user.getName());
        } else {
            usernameButton.setText("Anonymous");
        }
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
    private void handleBooksMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("home.fxml");
    }

    @FXML
    private void handleAddBooksMenu() {
        SceneManager.getInstance().clearStack();
        SceneManager.getInstance().pushSubScene("addBooks.fxml");
    }

    @FXML
    private void handleSettingsMenu() {
//        SceneManager.getInstance().clearStack();
//        SceneManager.getInstance().pushSubScene("settings.fxml");
    }

    @SneakyThrows
    @FXML
    private void handleLogoutMenu() {
        SessionManager.user = null;
        SceneManager.getInstance().setScene("auth/login.fxml");
    }
}
