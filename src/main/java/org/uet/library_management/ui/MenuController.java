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

//    @FXML
//    private void handleHomeMenu() {
//        SceneManager.getInstance().setSubScene("home.fxml");
//    }
}
