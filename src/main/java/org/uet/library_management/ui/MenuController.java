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
import org.uet.library_management.SceneManager;
import org.uet.library_management.tools.Mediator;

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

    @FXML public ImageView homeImageView;
    @FXML public ImageView getAllImageView;
    @FXML public ImageView bookshelfImageView;
    @FXML public ImageView bookmarkImageView;
    @FXML public ImageView finishedImageView;
    @FXML public ImageView booksImageView;
    @FXML public ImageView addBooksImageView;
    @FXML public ImageView usernameImageView;

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
                                Mediator.getInstance().setText(searchTextField.getText());
                                SceneManager.getInstance().setSubScene("search/suggestSearch.fxml");
                            } else {
                                SceneManager.getInstance().setSubScene("search/search.fxml");
                            }
                        });
                    }
                }, 300);
            }
        });

        // Set mouse event handlers
        searchTextField.setOnMouseClicked(this::handleSearchTextFieldMouseClick);

        homeButton.setOnMouseEntered(this::handleHomeButtonMouseEnter);
        homeButton.setOnMouseExited(this::handleHomeButtonMouseExit);

        bookshelfButton.setOnMouseEntered(this::handleBookshelfButtonMouseEnter);
        bookshelfButton.setOnMouseExited(this::handleBookshelfButtonMouseExit);

        getAllButton.setOnMouseEntered(this::handleGetAllButtonMouseEnter);
        getAllButton.setOnMouseExited(this::handleGetAllButtonMouseExit);

        bookmarkButton.setOnMouseEntered(this::handleBookmarkButtonMouseEnter);
        bookmarkButton.setOnMouseExited(this::handleBookmarkButtonMouseExit);

        finishedButton.setOnMouseEntered(this::handleFinishedButtonMouseEnter);
        finishedButton.setOnMouseExited(this::handleFinishedButtonMouseExit);

        booksButton.setOnMouseEntered(this::handleBooksButtonMouseEnter);
        booksButton.setOnMouseExited(this::handleBooksButtonMouseExit);

        addBooksButton.setOnMouseEntered(this::handleAddBooksButtonMouseEnter);
        addBooksButton.setOnMouseExited(this::handleAddBooksButtonMouseExit);
    }

    private void handleSearchTextFieldMouseClick(MouseEvent event) {
        SceneManager.getInstance().setSubScene("search/search.fxml");
    }

    private void handleHomeButtonMouseEnter(MouseEvent event) {
        homeImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "home-white.png")));
    }

    private void handleHomeButtonMouseExit(MouseEvent event) {
        homeImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "home.png")));
    }

    private void handleBookshelfButtonMouseEnter(MouseEvent mouseEvent) {
        bookshelfImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "bookshelf-white.png")));
    }

    private void handleBookshelfButtonMouseExit(MouseEvent mouseEvent) {
        bookshelfImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "bookshelf.png")));
    }

    private void handleGetAllButtonMouseEnter(MouseEvent event) {
        getAllImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "getAll-white.png")));
    }

    private void handleGetAllButtonMouseExit(MouseEvent event) {
        getAllImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "getAll.png")));
    }

    private void handleBookmarkButtonMouseEnter(MouseEvent mouseEvent) {
        bookmarkImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "bookmark-white.png")));
    }

    private void handleBookmarkButtonMouseExit(MouseEvent mouseEvent) {
        bookmarkImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "bookmark.png")));
    }

    private void handleFinishedButtonMouseEnter(MouseEvent mouseEvent) {
        finishedImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "finished-white.png")));
    }

    private void handleFinishedButtonMouseExit(MouseEvent mouseEvent) {
        finishedImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "finished.png")));
    }

    private void handleBooksButtonMouseEnter(MouseEvent mouseEvent) {
        booksImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "books-white.png")));
    }

    private void handleBooksButtonMouseExit(MouseEvent mouseEvent) {
        booksImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "books.png")));
    }

    private void handleAddBooksButtonMouseEnter(MouseEvent event) {
        addBooksImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "upload-white.png")));
    }

    private void handleAddBooksButtonMouseExit(MouseEvent event) {
        addBooksImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "upload.png")));
    }

    @FXML
    private void handleHomeMenu() {
        SceneManager.getInstance().setSubScene("home.fxml");
    }

    @FXML
    private void handleBookshelfMenu() {
        SceneManager.getInstance().setSubScene("bookshelf.fxml");
    }

    @FXML
    private void handleGetAllMenu() {
        SceneManager.getInstance().setSubScene("getAll.fxml");
    }

    @FXML
    private void handleBookmarkMenu() {
        SceneManager.getInstance().setSubScene("bookmark.fxml");
    }

    @FXML
    private void handleFinishedMenu() {
        SceneManager.getInstance().setSubScene("finished.fxml");
    }

    @FXML
    private void handleBooksMenu() {
        SceneManager.getInstance().setSubScene("home.fxml");
    }

    @FXML
    private void handleAddBooksMenu() {
        SceneManager.getInstance().setSubScene("addBooks.fxml");
    }
}
