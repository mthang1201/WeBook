package org.uet.library_management.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.uet.library_management.SceneManager;

public class MenuController {
    public TextField searchTextField;
    public Button homeButton;
    public Button getAllButton;
    public Button addBooksButton;

    public ImageView homeImageView;
    public ImageView getAllImageView;
    public ImageView addBooksImageView;

    @FXML
    public void initialize() {
        // Set mouse event handlers
        searchTextField.setOnMouseClicked(this::handleSearchTextFieldMouseClick);
        homeButton.setOnMouseEntered(this::handleHomeButtonMouseEnter);
        homeButton.setOnMouseExited(this::handleHomeButtonMouseExit);

        getAllButton.setOnMouseEntered(this::handleGetAllButtonMouseEnter);
        getAllButton.setOnMouseExited(this::handleGetAllButtonMouseExit);

        addBooksImageView.setOnMouseEntered(this::handleAddBooksButtonMouseEnter);
        addBooksImageView.setOnMouseExited(this::handleAddBooksButtonMouseExit);
    }

    private void handleSearchTextFieldMouseClick(MouseEvent event) {
        SceneManager.getInstance().setSubScene("search.fxml");
    }

    private void handleHomeButtonMouseEnter(MouseEvent event) {
        homeImageView.setImage(new Image(getClass().getResourceAsStream("/org/uet/library_management/icons/home-white.png")));
    }

    private void handleHomeButtonMouseExit(MouseEvent event) {
        homeImageView.setImage(new Image(getClass().getResourceAsStream("/org/uet/library_management/icons/home.png")));
    }

    private void handleGetAllButtonMouseEnter(MouseEvent event) {
        getAllImageView.setImage(new Image(getClass().getResourceAsStream("/org/uet/library_management/icons/getAll-white.png")));
    }

    private void handleGetAllButtonMouseExit(MouseEvent event) {
        getAllImageView.setImage(new Image(getClass().getResourceAsStream("/org/uet/library_management/icons/getAll.png")));
    }

    private void handleAddBooksButtonMouseEnter(MouseEvent event) {
        getAllImageView.setImage(new Image(getClass().getResourceAsStream("/org/uet/library_management/icons/upload-white.png")));
    }

    private void handleAddBooksButtonMouseExit(MouseEvent event) {
        getAllImageView.setImage(new Image(getClass().getResourceAsStream("/org/uet/library_management/icons/upload.png")));
    }

    @FXML
    private void handleLibraryMenu() {
        SceneManager.getInstance().setSubScene("hello-view.fxml");
    }

    @FXML
    private void handleHomeMenu() {
        SceneManager.getInstance().setSubScene("home.fxml");
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
        SceneManager.getInstance().setSubScene("books.fxml");
    }

    public void handleAddBooksMenu(ActionEvent actionEvent) {
    }

//    @FXML
//    private void handleHomeMenu() {
//        SceneManager.getInstance().setSubScene("home.fxml");
//    }
}
