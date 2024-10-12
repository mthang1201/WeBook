package org.uet.library_management.entities.documents;

import com.google.api.services.books.v1.model.Volume;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Document {
    private String publisher;
    private int pageCount;
    private double averageRating;
    private int ratingsCount;
    private String printType;
    private String isbn_10;
    private String isbn_13;
    private String imageLinks;
    private String maturityRating;

    public Book(String title, String authors, String publisher, String publishedDate, String description,
                String categories, Integer pageCount, Double averageRating, String maturityRating,
                String printType, String language, String isbn_10, String isbn_13, String imageLinks) {
        super(title, authors, publishedDate, description, categories, language);
        this.publisher = publisher;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.maturityRating = maturityRating;
        this.printType = printType;
        this.isbn_10 = isbn_10;
        this.isbn_13 = isbn_13;
        this.imageLinks = imageLinks;
    }
}
