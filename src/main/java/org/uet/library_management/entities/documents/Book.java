package org.uet.library_management.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Document {
    private String publisher;

    private String isbn;

    private int pageCount;

    private double averageRating;

    private int ratingsCount;

    private String imageLinks;

    private String maturityRating;

    private String printType;
}
