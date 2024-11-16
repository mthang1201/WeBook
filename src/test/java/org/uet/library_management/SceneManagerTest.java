//package org.uet.library_management;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.IOException;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class SceneManagerTest {
//
//    private SceneManager sceneManager;
//
//    @Mock
//    private Stage stage;
//
//    @Mock
//    private FXMLLoader fxmlLoader;
//
//    @Mock
//    private Scene scene;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//
//        sceneManager = SceneManager.getInstance();
//        sceneManager.setStage(stage);
//    }
//
//    @Test
//    public void testSetScene() throws IOException {
//        when(fxmlLoader.load()).thenReturn(new Object());
//        when(fxmlLoader.getResource("ui/sampleScene")).thenReturn(fxmlLoader);
//        when(scene.getStylesheets()).thenReturn(new ArrayList<>());
//        when(stage.setScene(scene)).thenReturn(new Object());
//
//        sceneManager.setScene("sampleScene");
//
//        verify(fxmlLoader).load();
//        verify(fxmlLoader).getResource("ui/sampleScene");
//        verify(scene).getStylesheets();
//        verify(stage).setScene(scene);
//        verify(stage).show();
//    }
//}