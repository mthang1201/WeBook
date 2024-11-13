package org.uet.library_management.tools;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlertUtilTest {

    @Test
    void testShowInformationsDialogWithoutGraphic() {
        String title = "INFO TITLE";
        String header = "INFO HEADER";
        String content = "INFO CONTENT";

        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.doNothing().when(mockAlert).showAndWait();

        Mockito.when(mockAlert.getAlertType()).thenReturn(Alert.AlertType.INFORMATION);

        ImageLoaderUtil imageLoaderUtil = Mockito.mock(ImageLoaderUtil.class);
        when(imageLoaderUtil.resolveImageUrl(any())).thenReturn("URL");

        ImageView imageView = new ImageView();

        Mockito.when(mockAlert.getGraphic()).thenReturn(imageView);

        AlertUtil.showInformationsDialog(title, header, content, null);
        verify(mockAlert, times(1)).showAndWait();
        verify(mockAlert, times(1)).setTitle(title);
        verify(mockAlert, times(1)).setHeaderText(header);
        verify(mockAlert, times(1)).setContentText(content);
        assertNull(mockAlert.getGraphic());
    }

    @Test
    void testShowInformationsDialogWithGraphic() {
        String title = "INFO TITLE";
        String header = "INFO HEADER";
        String content = "INFO CONTENT";
        String graphicImg = "IMG_URL";

        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.doNothing().when(mockAlert).showAndWait();

        Mockito.when(mockAlert.getAlertType()).thenReturn(Alert.AlertType.INFORMATION);

        ImageLoaderUtil imageLoaderUtil = Mockito.mock(ImageLoaderUtil.class);
        when(imageLoaderUtil.resolveImageUrl(any())).thenReturn("URL");

        ImageView imageView = new ImageView();

        Mockito.when(mockAlert.getGraphic()).thenReturn(imageView);

        AlertUtil.showInformationsDialog(title, header, content, graphicImg);
        verify(mockAlert, times(1)).showAndWait();
        verify(mockAlert, times(1)).setTitle(title);
        verify(mockAlert, times(1)).setHeaderText(header);
        verify(mockAlert, times(1)).setContentText(content);
        assertNotNull(mockAlert.getGraphic());
    }

    @Test
    void testShowErrorDialogWithoutGraphic() {
        String title = "ERROR TITLE";
        String header = "ERROR HEADER";
        String content = "ERROR CONTENT";

        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.doNothing().when(mockAlert).showAndWait();

        Mockito.when(mockAlert.getAlertType()).thenReturn(Alert.AlertType.ERROR);

        ImageLoaderUtil imageLoaderUtil = Mockito.mock(ImageLoaderUtil.class);
        when(imageLoaderUtil.resolveImageUrl(any())).thenReturn("URL");

        ImageView imageView = new ImageView();

        Mockito.when(mockAlert.getGraphic()).thenReturn(imageView);

        AlertUtil.showErrorAlert(title, header, content, null);
        verify(mockAlert, times(1)).showAndWait();
        verify(mockAlert, times(1)).setTitle(title);
        verify(mockAlert, times(1)).setHeaderText(header);
        verify(mockAlert, times(1)).setContentText(content);
        assertNull(mockAlert.getGraphic());
    }

    @Test
    void testShowErrorDialogWithGraphic() {
        String title = "ERROR TITLE";
        String header = "ERROR HEADER";
        String content = "ERROR CONTENT";
        String graphicImg = "IMG_URL";

       Alert mockAlert = Mockito.mock(Alert.class);
       Mockito.doNothing().when(mockAlert).showAndWait();

       Mockito.when(mockAlert.getAlertType()).thenReturn(Alert.AlertType.ERROR);

        ImageLoaderUtil imageLoaderUtil = Mockito.mock(ImageLoaderUtil.class);
        when(imageLoaderUtil.resolveImageUrl(any())).thenReturn("URL");

        ImageView imageView = new ImageView();

        Mockito.when(mockAlert.getGraphic()).thenReturn(imageView);

        AlertUtil.showErrorAlert(title, header, content, graphicImg);
        verify(mockAlert, times(1)).showAndWait();
        verify(mockAlert, times(1)).setTitle(title);
        verify(mockAlert, times(1)).setHeaderText(header);
        verify(mockAlert, times(1)).setContentText(content);
        assertNotNull(mockAlert.getGraphic());
    }

    @Test
    void testShowConfirmationAlertWithoutGraphic() {
        String title = "CONFIRMATION TITLE";
        String header = "CONFIRMATION HEADER";
        String content = "CONFIRMATION CONTENT";

        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.doNothing().when(mockAlert).showAndWait();

        Mockito.when(mockAlert.getAlertType()).thenReturn(Alert.AlertType.CONFIRMATION);

        ImageLoaderUtil imageLoaderUtil = Mockito.mock(ImageLoaderUtil.class);
        when(imageLoaderUtil.resolveImageUrl(any())).thenReturn("URL");

        ImageView imageView = new ImageView();

        Mockito.when(mockAlert.getGraphic()).thenReturn(imageView);

        AlertUtil.showConfirmationAlert(title, header, content, null);
        verify(mockAlert, times(1)).showAndWait();
        verify(mockAlert, times(1)).setTitle(title);
        verify(mockAlert, times(1)).setHeaderText(header);
        verify(mockAlert, times(1)).setContentText(content);
        assertNull(mockAlert.getGraphic());
    }

    @Test
    void testShowConfirmationAlertWithGraphic() {
        String title = "CONFIRMATION TITLE";
        String header = "CONFIRMATION HEADER";
        String content = "CONFIRMATION CONTENT";
        String graphicImg = "IMG_URL";

        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.doNothing().when(mockAlert).showAndWait();

        Mockito.when(mockAlert.getAlertType()).thenReturn(Alert.AlertType.CONFIRMATION);

        ImageLoaderUtil imageLoaderUtil = Mockito.mock(ImageLoaderUtil.class);
        when(imageLoaderUtil.resolveImageUrl(any())).thenReturn("URL");

        ImageView imageView = new ImageView();

        Mockito.when(mockAlert.getGraphic()).thenReturn(imageView);

        AlertUtil.showConfirmationAlert(title, header, content, graphicImg);
        verify(mockAlert, times(1)).showAndWait();
        verify(mockAlert, times(1)).setTitle(title);
        verify(mockAlert, times(1)).setHeaderText(header);
        verify(mockAlert, times(1)).setContentText(content);
        assertNotNull(mockAlert.getGraphic());
    }

    @Test
    void testShowWarningAlertWithoutGraphic() {
        String title = "WARNING TITLE";
        String header = "WARNING HEADER";
        String content = "WARNING CONTENT";

        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.doNothing().when(mockAlert).showAndWait();

        Mockito.when(mockAlert.getAlertType()).thenReturn(Alert.AlertType.WARNING);

        ImageLoaderUtil imageLoaderUtil = Mockito.mock(ImageLoaderUtil.class);
        when(imageLoaderUtil.resolveImageUrl(any())).thenReturn("URL");

        ImageView imageView = new ImageView();

        Mockito.when(mockAlert.getGraphic()).thenReturn(imageView);

        AlertUtil.showWarningAlert(title, header, content, null);
        verify(mockAlert, times(1)).showAndWait();
        verify(mockAlert, times(1)).setTitle(title);
        verify(mockAlert, times(1)).setHeaderText(header);
        verify(mockAlert, times(1)).setContentText(content);
        assertNull(mockAlert.getGraphic());
    }

    @Test
    void testShowWarningAlertWithGraphic() {
        String title = "WARNING TITLE";
        String header = "WARNING HEADER";
        String content = "WARNING CONTENT";
        String graphicImg = "IMG_URL";

        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.doNothing().when(mockAlert).showAndWait();

        Mockito.when(mockAlert.getAlertType()).thenReturn(Alert.AlertType.WARNING);

        ImageLoaderUtil imageLoaderUtil = Mockito.mock(ImageLoaderUtil.class);
        when(imageLoaderUtil.resolveImageUrl(any())).thenReturn("URL");

        ImageView imageView = new ImageView();

        Mockito.when(mockAlert.getGraphic()).thenReturn(imageView);

        AlertUtil.showWarningAlert(title, header, content, graphicImg);
        verify(mockAlert, times(1)).showAndWait();
        verify(mockAlert, times(1)).setTitle(title);
        verify(mockAlert, times(1)).setHeaderText(header);
        verify(mockAlert, times(1)).setContentText(content);
        assertNotNull(mockAlert.getGraphic());
    }
}
