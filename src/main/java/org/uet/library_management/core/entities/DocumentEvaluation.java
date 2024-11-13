package org.uet.library_management.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Represents an evaluation or a review of a document.
 * Each evaluation contains details such as the evaluation ID,
 * ISBN-13 of the document, user ID of the reviewer, rating,
 * comment, and timestamp of creation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEvaluation {
    private int evaluationId;
    private String isbn13;
    private int userId;
    private int rating;
    private String comment;
    private Timestamp createdAt;



    /**
     * Constructs a new DocumentEvaluation instance with the given parameters.
     *
     * @param isbn13 The ISBN-13 code of the document being evaluated.
     * @param userId The ID of the user who is creating the evaluation.
     * @param rating The rating given by the user for the document.
     * @param comment The comment provided by the user for the document.
     * @param createdAt The timestamp when the evaluation was created.
     */
    public DocumentEvaluation(String isbn13, int userId, int rating, String comment, Timestamp createdAt) {
        this.isbn13 = isbn13;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
