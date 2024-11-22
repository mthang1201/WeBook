package org.uet.library_management.core.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A class representing a Book, which extends the Document class.
 * It contains various attributes that are specific to books such as publisher,
 * ISBNs, page count, average rating, image links, and more.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Document {

    private String publisher;
    private String isbn10;
    private String isbn13;
    private int pageCount;
    private Double averageRating;
    private Integer ratingsCount;
    private String imageLinks;
    private String maturityRating;
    private String printType;

    /**
     * Constructs a new Book instance with the specified properties.
     *
     * @param title The title of the book.
     * @param authors The authors of the book.
     * @param publisher The publisher of the book.
     * @param publishedDate The date when the book was published.
     * @param description A brief description of the book.
     * @param categories The categories or genres of the book.
     * @param pageCount The number of pages in the book.
     * @param ratingsCount The number of ratings the book has received.
     * @param averageRating The average rating of the book.
     * @param maturityRating The maturity rating of the book.
     * @param printType The print type of the book.
     * @param language The language of the book.
     * @param isbn10 The 10-digit ISBN of the book.
     * @param isbn13 The 13-digit ISBN of the book.
     * @param imageLinks The links to images of the book, such as cover images.
     */
    public Book(String title, String authors, String publisher, String publishedDate, String description,
                String categories, Integer pageCount, Integer ratingsCount, Double averageRating, String maturityRating,
                String printType, String language, String isbn10, String isbn13, String imageLinks) {
        super(title, authors, publishedDate, description, categories, language);
        this.publisher = publisher;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.pageCount = pageCount;
        this.ratingsCount = ratingsCount;
        this.averageRating = averageRating;
        this.maturityRating = maturityRating;
        this.printType = printType;
        this.imageLinks = imageLinks;
    }
}
