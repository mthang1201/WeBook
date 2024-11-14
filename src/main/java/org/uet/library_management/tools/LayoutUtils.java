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

/**
 * Utility class providing several static methods for managing layout and creating UI components.
 */
public class LayoutUtils {

    /**
     * Sets the margin around a Node within a VBox.
     *
     * @param node   the Node to which the margin will be applied
     * @param top    the margin at the top of the Node
     * @param right  the margin at the right of the Node
     * @param bottom the margin at the bottom of the Node
     * @param left   the margin at the left of the Node
     */
    public static void setVBoxNodeMargin(Node node, double top, double right, double bottom, double left) {
        VBox.setMargin(node, new Insets(top, right, bottom, left));
    }

    /**
     * Sets the margin around a Node within an HBox.
     *
     * @param node   the Node to which the margin will be applied
     * @param top    the margin at the top of the Node
     * @param right  the margin at the right of the Node
     * @param bottom the margin at the bottom of the Node
     * @param left   the margin at the left of the Node
     */
    public static void setHBoxNodeMargin(Node node, double top, double right, double bottom, double left) {
        HBox.setMargin(node, new Insets(top, right, bottom, left));
    }
    /**
     * Sets the margin around all child nodes within a VBox.
     *
     * @param vbox   the VBox whose children will have their margins set
     * @param top    the margin at the top of each child node
     * @param right  the margin at the right of each child node
     * @param bottom the margin at the bottom of each child node
     * @param left   the margin at the left of each child node
     */
    public static void setVboxMargin(VBox vbox, double top, double right, double bottom, double left) {
        for (Node node : vbox.getChildren()) {
            VBox.setMargin(node, new Insets(top, right, bottom, left));
        }
    }

    /**
     * Sets the margin for all children nodes in a VBox starting from the second child.
     *
     * @param vbox   the VBox whose children (excluding the first) will have their margins set
     * @param top    the margin at the top of each child node
     * @param right  the margin at the right of each child node
     * @param bottom the margin at the bottom of each child node
     * @param left   the margin at the left of each child node
     */
    public static void setVboxMarginAfterHeader(VBox vbox, double top, double right, double bottom, double left) {
        for (int i = 1; i < vbox.getChildren().size(); i++) {
            Node node = vbox.getChildren().get(i);
            VBox.setMargin(node, new Insets(top, right, bottom, left));
        }
    }

    /**
     * Sets the margin around all child nodes within an HBox.
     *
     * @param hbox   the HBox whose children will have their margins set
     * @param top    the margin at the top of each child node
     * @param right  the margin at the right of each child node
     * @param bottom the margin at the bottom of each child node
     * @param left   the margin at the left of each child node
     */
    public static void setHBoxMargin(HBox hbox, double top, double right, double bottom, double left) {
        for (Node node : hbox.getChildren()) {
            HBox.setMargin(node, new Insets(top, right, bottom, left));
        }
    }

    /**
     * Creates and configures an ImageView with the specified image and dimensions.
     *
     * @param image         the image to be displayed in the ImageView
     * @param fitWidth      the width to fit the image to
     * @param fitHeight     the height to fit the image to
     * @param preserveRatio a boolean indicating whether to preserve the aspect ratio of the image
     * @return a configured ImageView instance displaying the specified image
     */
    public static ImageView createImageView(Image image, double fitWidth, double fitHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(image);

        // Set the fit width and height
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);

        // Optionally preserve the aspect ratio
        imageView.setPreserveRatio(preserveRatio);

        return imageView;
    }

    /**
     * Creates a list of ImageViews from the provided list of Images, configuring each ImageView
     * with the specified dimensions and aspect ratio preservation setting.
     *
     * @param starImages    the list of images to be converted into ImageViews
     * @param fitWidth      the width to fit each image to
     * @param fitHeight     the height to fit each image to
     * @param preserveRatio a boolean indicating whether to preserve the aspect ratio of each image
     * @return a list of ImageView instances displaying the provided images
     */
    public static List<ImageView> createListImageViews(List<Image> starImages, double fitWidth, double fitHeight, boolean preserveRatio) {
        List<ImageView> ImageViews = new ArrayList<>();
        for (Image starImage : starImages) {
            ImageViews.add(createImageView(starImage, fitWidth, fitHeight, preserveRatio));
        }
        return ImageViews;
    }

    /**
     * Creates a VBox containing a styled review box with user avatar, rating information,
     * and comment based on the provided DocumentEvaluation.
     *
     * @param evaluation the DocumentEvaluation containing the user's review details
     * @return a VBox containing the visual representation of the review
     */
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

