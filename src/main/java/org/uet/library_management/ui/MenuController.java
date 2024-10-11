package org.uet.library_management.ui;

import javafx.fxml.FXML;
import org.uet.library_management.SceneManager;

public class MenuController {
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

//    @FXML
//    private void handleHomeMenu() {
//        SceneManager.getInstance().setSubScene("home.fxml");
//    }
}
