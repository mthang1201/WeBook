package org.uet.library_management.entities.documents;

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
}
