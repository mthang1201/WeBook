package org.uet.library_management.tools;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;
import org.uet.library_management.core.entities.documents.Book;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UIBuilderTest {

    @Test
    void testCreateFlowPane() throws Exception {
        // Arrange
        List<Book> books = Arrays.asList(
                new Book("Book 1", "Author 1", null, null, null, null, null, null, null, null, null, null, null, "1234", null),
                new Book("Book 2", "Author 2", null, null, null, null, null, null, null, null, null, null, null, "1234", null)
        );

        // Act
        FlowPane flowPane = UIBuilder.createFlowPane(books);

        // Assert
        assertEquals(2, flowPane.getChildren().size());
        VBox vbox = (VBox) flowPane.getChildren().get(0);
        assertEquals(3, vbox.getChildren().size());

        ImageView imageView = (ImageView) vbox.getChildren().get(0);
        assertNotNull(imageView.getImage());

        Label titleLabel = (Label) vbox.getChildren().get(1);
        assertEquals("Book 1", titleLabel.getText());

        Label authorsLabel = (Label) vbox.getChildren().get(2);
        assertEquals("Author 1", authorsLabel.getText());
    }

    @Test
    void testGenerateSuggestions_EmptyList() throws Exception {
        // Arrange
        List<String> keywords = Arrays.asList();

        // Act
        VBox result = UIBuilder.generateSuggestions(keywords);

        // Assert
        assertEquals(0, result.getChildren().size());
    }

    @Test
    void testGenerateSuggestions_NonEmptyList() throws Exception {
        // Arrange
        List<String> keywords = Arrays.asList("Keyword1", "Keyword2");

        // Act
        VBox result = UIBuilder.generateSuggestions(keywords);

        // Assert
        assertEquals(4, result.getChildren().size());
        assertEquals("Keyword1", ((Label)((HBox)result.getChildren().get(0)).getChildren().get(1)).getText());
        assertEquals("Keyword2", ((Label)((HBox)result.getChildren().get(2)).getChildren().get(1)).getText());
    }
}
