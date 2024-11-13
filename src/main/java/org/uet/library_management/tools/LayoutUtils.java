package org.uet.library_management.tools;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.uet.library_management.core.entities.DocumentEvaluation;
import org.uet.library_management.core.repositories.UserAvatarRepository;

import java.util.ArrayList;
import java.util.List;

public class LayoutUtils {

    public static void setVBoxNodeMargin(Node node, double top, double right, double bottom, double left) {
        VBox.setMargin(node, new Insets(top, right, bottom, left));
    }

    public static void setHBoxNodeMargin(Node node, double top, double right, double bottom, double left) {
        HBox.setMargin(node, new Insets(top, right, bottom, left));
    }
    public static void setVboxMargin(VBox vbox, double top, double right, double bottom, double left) {
        for (Node node : vbox.getChildren()) {
            VBox.setMargin(node, new Insets(top, right, bottom, left));
        }
    }

    public static void setVboxMarginAfterHeader(VBox vbox, double top, double right, double bottom, double left) {
        for (int i = 1; i < vbox.getChildren().size(); i++) {
            Node node = vbox.getChildren().get(i);
            VBox.setMargin(node, new Insets(top, right, bottom, left));
        }
    }

    public static void setHBoxMargin(HBox hbox, double top, double right, double bottom, double left) {
        for (Node node : hbox.getChildren()) {
            HBox.setMargin(node, new Insets(top, right, bottom, left));
        }
    }

    public static ImageView createImageView(Image image, double fitWidth, double fitHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(image);

        // Set the fit width and height
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);

        // Optionally preserve the aspect ratio
        imageView.setPreserveRatio(preserveRatio);

        return imageView;
    }

    public static List<ImageView> createListImageViews(List<Image> starImages, double fitWidth, double fitHeight, boolean preserveRatio) {
        List<ImageView> ImageViews = new ArrayList<>();
        for (Image starImage : starImages) {
            ImageViews.add(createImageView(starImage, fitWidth, fitHeight, preserveRatio));
        }
        return ImageViews;
    }

    public static VBox createReviewBox(DocumentEvaluation evaluation) {
        VBox reviewBox = new VBox(10);
        reviewBox.setPadding(new Insets(10));

        HBox avatarAndRatingInfo = new HBox(10);
        avatarAndRatingInfo.setStyle("-fx-alignment: CENTER_LEFT");

        UserAvatarRepository avatarRepository = new UserAvatarRepository();
        Image avatarImg = avatarRepository.findByUserId(SessionManager.user.getUserId());
        ImageView avatar = createImageView(avatarImg, 80,80, true);
        avatar.setStyle("-fx-border-radius: 50%;");

        HBox nameAndTimeStamp = new HBox(30);
        Label name = new Label(SessionManager.user.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label createAt = new Label(String.valueOf(evaluation.getCreatedAt()));
        createAt.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");
        nameAndTimeStamp.getChildren().addAll(name, createAt);

        HBox ratings = new HBox(5);
        List<ImageView> stars = createListImageViews(ImageLoaderUtil.getStarImages(evaluation.getRating()),
                20, 20, true);
        ratings.getChildren().addAll(stars);

        VBox infoAndRatings = new VBox(5);
        infoAndRatings.getChildren().addAll(nameAndTimeStamp,ratings);
        avatarAndRatingInfo.getChildren().addAll(avatar, infoAndRatings);

        Text commentText = new Text(evaluation.getComment());
        commentText.setWrappingWidth(300);
        commentText.setStyle("-fx-font-size: 13px; -fx-text-fill: black;");

        reviewBox.getChildren().addAll(avatarAndRatingInfo, commentText);

        return reviewBox;
    }


}

