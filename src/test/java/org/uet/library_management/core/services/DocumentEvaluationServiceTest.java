package org.uet.library_management.core.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uet.library_management.core.entities.DocumentEvaluation;
import org.uet.library_management.core.repositories.DocumentEvaluationRepository;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DocumentEvaluationServiceTest {
    DocumentEvaluationService documentEvaluationService;
    DocumentEvaluationRepository documentEvaluationRepository;

    @Test
    void getUserReviewShouldReturnCorrectEvaluation() {
        // Arrange
        String isbn = "9789295055024";
        int userId = 1;
        var expectedEvaluation = new DocumentEvaluation(isbn, userId, 5, "Review comment", new Timestamp(System.currentTimeMillis()));
        documentEvaluationRepository = mock(DocumentEvaluationRepository.class);
        when(documentEvaluationRepository.getUserReview(isbn, userId)).thenReturn(expectedEvaluation);
        documentEvaluationService = new DocumentEvaluationService(documentEvaluationRepository);

        // Act
        DocumentEvaluation result = documentEvaluationService.getUserReview(isbn, userId);

        // Assert
        assertEquals(expectedEvaluation, result);
    }

    @Test
    void getUserReviewShouldReturnNullForNonExistingReview() {
        // Arrange
        String isbn = "9789295055024";
        int userId = 9999;
        documentEvaluationRepository = mock(DocumentEvaluationRepository.class);
        when(documentEvaluationRepository.getUserReview(isbn, userId)).thenReturn(null);
        documentEvaluationService = new DocumentEvaluationService(documentEvaluationRepository);

        // Act
        DocumentEvaluation result = documentEvaluationService.getUserReview(isbn, userId);

        // Assert
        assertNull(result);
    }
}