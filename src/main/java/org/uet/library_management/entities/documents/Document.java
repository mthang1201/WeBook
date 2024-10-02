package org.uet.library_management.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Document {
    private String documentId;

    private String documentName;

    private String genre;

    private String authorName;

    private String documentDescription;

    private String publishedDate;

    private int quantityInStock;
}
