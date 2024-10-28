package org.uet.library_management.tools;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.uet.library_management.core.entities.documents.Book;

import java.util.List;

public class UIBuilder {

    public static FlowPane createFlowPane(List<Book> books) {
        FlowPane flowPane = new FlowPane();

        for (Book book : books) {
            VBox vbox = new VBox();

            Image image = ImageCacheManager.getInstance().loadImage(book.getIsbn13(), book.getTitle(), book.getImageLinks());
//            Image image = new Image(book.getImageLinks(), true);

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);

            Label titleLabel = new Label(book.getTitle());
            Label authorsLabel = new Label(book.getAuthors());

            vbox.getChildren().addAll(imageView, titleLabel, authorsLabel);

            flowPane.getChildren().add(vbox);
        }

        return flowPane;
    }
}
