package org.uet.library_management.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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



    public DocumentEvaluation(String isbn13, int userId, int rating, String comment, Timestamp createdAt) {
        this.isbn13 = isbn13;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
