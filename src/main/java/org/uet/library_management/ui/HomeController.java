package org.uet.library_management.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.uet.library_management.api.search.*;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.tools.RecommendationGenerator;
import org.uet.library_management.tools.UIBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * The HomeController class is responsible for initializing the UI components
 * and dynamically loading content based on user interaction with the scroll pane.
 * It uses recommendation generators to fill the UI with book recommendations.
 */
public class HomeController {
    @FXML
    private ScrollPane scrollpane;

    @FXML
    private VBox recommendBox;

    private boolean isLoading = false;
    private int index = 0;
    List<RecommendationGenerator> recommendationGeneratorList = new ArrayList<>();

    @FXML
    public void initialize() {
        List<String> randomGenres = RecommendationGenerator.getRandomGenre();
        List<Book> randomBooks = RecommendationGenerator.getRandomTitleFromBookmarks();

        recommendationGeneratorList.add(new RecommendationGenerator("recommendation",
                "Có thể bạn sẽ thích", null));
       for (int i = 0; i < 5; i++) {
           String genre = randomGenres.get(i);
           recommendationGeneratorList.add(new RecommendationGenerator(
                   genre,
                   genre,
                   new SearchByCategory()
           ));
       }

       for (int i = 0; i < 3; i++) {
            Book book = randomBooks.get(i);
           recommendationGeneratorList.add(new RecommendationGenerator(
                   book.getCategories(),
                   "Tương tự " + book.getTitle(),
                   new SearchByCategory())
           );
       }

        recommendationGeneratorList.add(new RecommendationGenerator(
                "e",
                "New Releases",
                new SearchByNewest()));

        recommendBox.getChildren().add(UIBuilder.generateHorizontalRecommendation(
                recommendationGeneratorList.get(0).getSearchTerm(),
                recommendationGeneratorList.get(0).getTitle(),
                recommendationGeneratorList.get(0).getSearchStrategy()));
        index++;
        recommendBox.getChildren().add(UIBuilder.generateHorizontalRecommendation(
                recommendationGeneratorList.get(1).getSearchTerm(),
                recommendationGeneratorList.get(1).getTitle(),
                recommendationGeneratorList.get(1).getSearchStrategy()));
        index++;

        loadHorizontalRecommendation();
    }

    private void loadHorizontalRecommendation() {
        scrollpane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0 && !isLoading && index < recommendationGeneratorList.size()) {
                isLoading = true;
                RecommendationGenerator recommendationGenerator = recommendationGeneratorList.get(index);
                recommendBox.getChildren().add(UIBuilder.generateHorizontalRecommendation(
                        recommendationGenerator.getSearchTerm(),
                        recommendationGenerator.getTitle(),
                        recommendationGenerator.getSearchStrategy()));
                index++;
                isLoading = false;
            }
        });
    }

}
