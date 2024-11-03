package org.uet.library_management;

import javafx.application.Application;
import javafx.stage.Stage;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.SessionManager;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().setStage(stage);
//        SceneManager.getInstance().setScene("admin/admin.fxml");
        UserService userService = new UserService();
        SessionManager.user = userService.findByName("abc").get(0);
        SceneManager.getInstance().setScene("main.fxml");
//        SceneManager.getInstance().setScene("auth/login.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}