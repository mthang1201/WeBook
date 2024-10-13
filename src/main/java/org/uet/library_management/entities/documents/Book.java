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
    private String isbn10;
    private String isbn13;
    private int pageCount;
    private double averageRating;
    private int ratingsCount;
    private String imageLinks;
    private String maturityRating;
    private String printType;

    public Book(String title, String authors, String publisher, String publishedDate, String description,
                String categories, Integer pageCount, Double averageRating, String maturityRating,
                String printType, String language, String isbn10, String isbn13, String imageLinks) {
        super(title, authors, publishedDate, description, categories, language);
        this.publisher = publisher;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.maturityRating = maturityRating;
        this.printType = printType;
        this.imageLinks = imageLinks;
    }
}
