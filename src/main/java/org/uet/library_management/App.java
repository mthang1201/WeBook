package org.uet.library_management;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.SessionManager;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;


import java.io.IOException;

/**
 * The App class extends the Application class and serves as the main entry point for the JavaFX application.
 * It initializes the primary stage, sets its dimensions, and loads the initial scene.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().setStage(stage);

        stage.setMinHeight(600);
        stage.setMinWidth(800);
//        stage.initStyle(StageStyle.DECORATED);
//        stage.setOpacity(0.889);
//        stage.setEffect(new GaussianBlur(10));


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