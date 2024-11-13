package org.uet.library_management.core.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * An abstract class representing a document with common attributes such as
 * document ID, title, authors, published date, description, categories, and language.
 *
 * This class provides constructors to create instances with or without an ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Document {
    private int documentId;
    private String title;
    private String authors;
    private String publishedDate;
    private String description;
    private String categories;
    private String language;

    /**
     * API constructor(not including ID)
     */
    public Document(String title, String authors, String publishedDate, String description, String categories, String language) {
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.description = description;
        this.categories = categories;
        this.language = language;
    }
}
