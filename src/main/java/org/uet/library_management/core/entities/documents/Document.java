package org.uet.library_management.core.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    private int availableCopies;

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
