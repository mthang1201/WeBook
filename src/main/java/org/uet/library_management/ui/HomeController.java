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
    private List<RecommendationGenerator> recommendationGeneratorList;

    /**
     * Initializes the HomeController by setting up a list of book recommendations.
     *
     * This method populates the recommendationGeneratorList with various types of
     * recommendations including general recommendations, genre-based recommendations,
     * and recommendations based on titles from bookmarks. It then updates the UI
     * with these recommendations.
     *
     * The following types of recommendations are added:
     * - General recommendations
     * - Genre-based recommendations for a limited set of random genres
     * - Recommendations based on a small set of random bookmarked titles
     * - New releases recommendations
     *
     * The method also handles updating the UI components to display the first
     * few of these recommendations and calls the `loadHorizontalRecommendation`
     * method to load additional recommendations.
     */
    @FXML
    public void initialize() {
        recommendationGeneratorList = new ArrayList<>();
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

    /**
     * Loads additional horizontal recommendations into the UI when the scroll pane is
     * scrolled to the bottom.
     *
     * This method listens for changes to the vertical scroll value of the scroll pane.
     * When the scroll reaches the bottom and more recommendations are available, it:
     * - Sets the `isLoading` flag to true to prevent concurrent loads.
     * - Retrieves the next `RecommendationGenerator` from the list.
     * - Generates a horizontal recommendation UI component using the `UIBuilder`.
     * - Adds this component to the `recommendBox`.
     * - Increments the index to point to the next recommendation.
     * - Resets the `isLoading` flag to false.
     */
    private void loadHorizontalRecommendation() {
        scrollpane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() >= 0.9 && !isLoading && index < recommendationGeneratorList.size()) {
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
