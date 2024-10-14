package org.uet.library_management.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.uet.library_management.SceneManager;

public class MenuController {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    public TextField searchTextField;
    public Button homeButton;
    public Button getAllButton;
    public Button addBooksButton;
    public Button usernameButton;

    public ImageView homeImageView;
    public ImageView getAllImageView;
    public ImageView addBooksImageView;
    public ImageView usernameImageView;

    @FXML
    public void initialize() {
        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    SceneManager.getInstance().setSubScene("search/suggestSearch.fxml");
                } else {
                    SceneManager.getInstance().setSubScene("search/search.fxml");
                }
            }
        });

        // Set mouse event handlers
        searchTextField.setOnMouseClicked(this::handleSearchTextFieldMouseClick);

        homeButton.setOnMouseEntered(this::handleHomeButtonMouseEnter);
        homeButton.setOnMouseExited(this::handleHomeButtonMouseExit);

        getAllButton.setOnMouseEntered(this::handleGetAllButtonMouseEnter);
        getAllButton.setOnMouseExited(this::handleGetAllButtonMouseExit);

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

    private void handleGetAllButtonMouseEnter(MouseEvent event) {
        getAllImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "getAll-white.png")));
    }

    private void handleGetAllButtonMouseExit(MouseEvent event) {
        getAllImageView.setImage(new Image(getClass().getResourceAsStream(PREFIX_ICONS + "getAll.png")));
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
    private void handleBookShelfMenu() {
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
