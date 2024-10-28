package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.search.SearchByAuthor;
import org.uet.library_management.api.search.SearchByGeneral;
import org.uet.library_management.api.search.SearchByTitle;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.api.sort.SortByAvgRating;
import org.uet.library_management.api.sort.SortByNewest;
import org.uet.library_management.api.sort.SortByOldest;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.ImageCacheManager;

import java.util.List;

public class HomeController {
    @FXML
    public ScrollPane scrollpane;

    @FXML
    public FlowPane flowPane;

    @FXML
    public void initialize() {
        scrollpane.setContent(flowPane);
        scrollpane.setFitToWidth(true);
        scrollpane.setPannable(true);

        flowPane.setPadding(new Insets(10,10,10,10));


    }
}
