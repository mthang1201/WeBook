package org.uet.library_management.tools;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

/**
 * Utility class for displaying different types of alert dialogs.
 */
public class AlertUtil {

    /**
     * Displays an information dialog with the specified title, header, content text, and an optional graphic image.
     *
     * @param title      the title of the information dialog
     * @param headerText the header text of the information dialog, may be null
     * @param content    the content text of the information dialog
     * @param graphicImg the URL of the graphic image to display in the dialog, may be null
     */
    public static void showInformationsDialog(String title, String headerText, String content, String graphicImg) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(title);
        informationAlert.setHeaderText(headerText);
        informationAlert.setContentText(content);

        if (graphicImg != null) {
            ImageView graphic = new ImageView(ImageLoaderUtil.resolveImageUrl(graphicImg));
            graphic.setFitHeight(50);
            graphic.setFitWidth(50);
            informationAlert.setGraphic(graphic);
        } else {
            informationAlert.setGraphic(null);
        }
        informationAlert.showAndWait();
    }

    /**
     * Displays an error alert dialog with the specified title, header, content text, and an optional graphic image.
     *
     * @param title      the title of the error alert
     * @param headerText the header text of the error alert, may be null
     * @param content    the content text of the error alert
     * @param graphicImg the URL of the graphic image to display in the alert, may be null
     */
    public static void showErrorAlert(String title, String headerText, String content, String graphicImg) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(content);
        if (graphicImg != null) {
            ImageView graphic = new ImageView(ImageLoaderUtil.resolveImageUrl(graphicImg));
            graphic.setFitHeight(50);
            graphic.setFitWidth(50);
            errorAlert.setGraphic(graphic);
        } else {
            errorAlert.setGraphic(null);
        }
        errorAlert.showAndWait();
    }

    /**
     * Displays a confirmation alert dialog with the specified title, header, content text, and an optional graphic image.
     *
     * @param title      the title of the confirmation alert
     * @param headerText the header text of the confirmation alert, may be null
     * @param content    the content text of the confirmation alert
     * @param graphicImg the URL of the graphic image to display in the alert, may be null
     */
    public static void showConfirmationAlert(String title, String headerText, String content, String graphicImg) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(title);
        confirmAlert.setHeaderText(headerText);
        confirmAlert.setContentText(content);
        if (graphicImg != null) {
            ImageView graphic = new ImageView(ImageLoaderUtil.resolveImageUrl(graphicImg));
            graphic.setFitHeight(50);
            graphic.setFitWidth(50);
            confirmAlert.setGraphic(graphic);
        } else {
            confirmAlert.setGraphic(null);
        }
        confirmAlert.showAndWait();
    }

    /**
     * Displays a warning alert dialog with the specified title, header, content text, and an optional graphic image.
     *
     * @param title      the title of the warning alert
     * @param headerText the header text of the warning alert, may be null
     * @param content    the content text of the warning alert
     * @param graphicImg the URL of the graphic image to display in the alert, may be null
     */
    public static void showWarningAlert(String title, String headerText, String content, String graphicImg) {
        Alert warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setTitle(title);
        warningAlert.setHeaderText(headerText);
        warningAlert.setContentText(content);
        if (graphicImg != null) {
            ImageView graphic = new ImageView(ImageLoaderUtil.resolveImageUrl(graphicImg));
            graphic.setFitHeight(50);
            graphic.setFitWidth(50);
            warningAlert.setGraphic(graphic);
        } else {
            warningAlert.setGraphic(null);
        }
        warningAlert.showAndWait();
    }

    /**
     * Creates and returns an information dialog with the specified title, header text, content, and an optional graphic image.
     *
     * @param title      the title of the information dialog
     * @param headerText the header text of the information dialog, may be null
     * @param content    the content text of the information dialog
     * @param graphicImg the URL of the graphic image to display in the dialog, may be null
     * @return an Alert object representing the information dialog
     */
    public static Alert createInformationDialog(String title, String headerText, String content, String graphicImg) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(title);
        informationAlert.setHeaderText(headerText);
        informationAlert.setContentText(content);

        if (graphicImg != null) {
            ImageView graphic = new ImageView(ImageLoaderUtil.resolveImageUrl(graphicImg));
            graphic.setFitHeight(50);
            graphic.setFitWidth(50);
            informationAlert.setGraphic(graphic);
        } else {
            informationAlert.setGraphic(null);
        }
        return informationAlert;
    }

    /**
     * Creates and returns an error alert dialog with the specified title, header, content text,
     * and an optional graphic image.
     *
     * @param title      the title of the error alert
     * @param headerText the header text of the error alert, may be null
     * @param content    the content text of the error alert
     * @param graphicImg the URL of the graphic image to display in the alert, may be null
     * @return an Alert object representing the error alert dialog
     */
    public static Alert createErrorAlert(String title, String headerText, String content, String graphicImg) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(content);

        if (graphicImg != null) {
            ImageView graphic = new ImageView(ImageLoaderUtil.resolveImageUrl(graphicImg));
            graphic.setFitHeight(50);
            graphic.setFitWidth(50);
            errorAlert.setGraphic(graphic);
        } else {
            errorAlert.setGraphic(null);
        }
        return errorAlert;
    }

    /**
     * Creates and returns a confirmation alert dialog with the specified title, header text, content,
     * and an optional graphic image.
     *
     * @param title      the title of the confirmation alert
     * @param headerText the header text of the confirmation alert, may be null
     * @param content    the content text of the confirmation alert
     * @param graphicImg the URL of the graphic image to display in the alert, may be null
     * @return an Alert object representing the confirmation alert
     */
    public static Alert createConfirmationAlert(String title, String headerText, String content, String graphicImg) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(title);
        confirmAlert.setHeaderText(headerText);
        confirmAlert.setContentText(content);

        if (graphicImg != null) {
            ImageView graphic = new ImageView(ImageLoaderUtil.resolveImageUrl(graphicImg));
            graphic.setFitHeight(50);
            graphic.setFitWidth(50);
            confirmAlert.setGraphic(graphic);
        } else {
            confirmAlert.setGraphic(null);
        }
        return confirmAlert;
    }

    /**
     * Creates and returns a warning alert dialog with the specified title, header text, content,
     * and an optional graphic image.
     *
     * @param title      the title of the warning alert
     * @param headerText the header text of the warning alert, may be null
     * @param content    the content text of the warning alert
     * @param graphicImg the URL of the graphic image to display in the alert, may be null
     * @return an Alert object representing the warning alert
     */
    public static Alert createWarningAlert(String title, String headerText, String content, String graphicImg) {
        Alert warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setTitle(title);
        warningAlert.setHeaderText(headerText);
        warningAlert.setContentText(content);

        if (graphicImg != null) {
            ImageView graphic = new ImageView(ImageLoaderUtil.resolveImageUrl(graphicImg));
            graphic.setFitHeight(50);
            graphic.setFitWidth(50);
            warningAlert.setGraphic(graphic);
        } else {
            warningAlert.setGraphic(null);
        }
        return warningAlert;
    }
}
