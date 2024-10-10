package org.uet.library_management.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Document {
    private int documentId;
    private String title;
    private List<String> authors;
    private String publishedDate;
    private String description;
    private List<String> categories;
    private String language;
    private int availableCopies;

    /**
     * API constructor(not including ID)
     */
    public Document(String title, List<String> authors, String publishedDate, String description, List<String> categories, String language) {
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.description = description;
        this.categories = categories;
        this.language = language;
    }
}
