package org.uet.library_management.tools;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public class AlertUtil {

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
