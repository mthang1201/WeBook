package org.uet.library_management.repositories.documents;

import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.entities.documents.Document;

public class BookRepository extends DocumentRepository<Book> {
    public BookRepository() {
        super();
    }

    @Override
    protected void loadDatabase() {
        db_table = "books";
    }

    @Override
    public void add(Document document) {
        Book book = (Book) document;
        String query = "INSERT INTO " + db_table + " (title, authors, publisher, " +
                "publishedDate, description, isbn10, isbn13, pageCount, categories, averageRating, " +
                "ratingsCount, imageLinks, language, maturityRating, printType, " +
                "availableCopies) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                book.getPublisher(), document.getPublishedDate(), document.getDescription(),
                book.getIsbn10(), book.getIsbn13(), book.getPageCount(), document.getCategories(),
                book.getAverageRating(), book.getRatingsCount(), book.getImageLinks(),
                document.getLanguage(), book.getMaturityRating(), book.getPrintType(),
                document.getAvailableCopies());
    }

//    public void addUsingIsbn(String isbn) {
//        Document document = BooksApiService.find(isbn);
//        Book book = (Book) document;
//        String query = "INSERT INTO " + db_table + " (title, authors, publisher, " +
//                "publishedDate, description, isbn, pageCount, categories, averageRating, " +
//                "ratingsCount, imageLinks, language, maturityRating, printType, " +
//                "availableCopies) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
//                book.getPublisher(), document.getPublishedDate(), document.getDescription(),
//                book.getIsbn(), book.getPageCount(), document.getCategories(),
//                book.getAverageRating(), book.getRatingsCount(), book.getImageLinks(),
//                document.getLanguage(), book.getMaturityRating(), book.getPrintType(),
//                document.getAvailableCopies());
//    }

    @Override
    public void update(Document document) {
        Book book = (Book) document;
        String query = "UPDATE " + db_table + " SET title = ?, authors = ?, publisher = ?, " +
                "publishedDate = ?, description = ?, isbn = ?, pageCount = ?, " +
                "categories = ?, averageRating = ?, ratingsCount = ?, imageLinks = ?, " +
                "language = ?, maturityRating = ?, printType = ?, availableCopies = ? " +
                "WHERE documentId = ?";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                book.getPublisher(), document.getPublishedDate(), document.getDescription(),
                book.getIsbn10(), book.getIsbn13(), book.getPageCount(), document.getCategories(),
                book.getAverageRating(), book.getRatingsCount(), book.getImageLinks(),
                document.getLanguage(), book.getMaturityRating(), book.getPrintType(),
                document.getAvailableCopies(), document.getDocumentId());
    }
}
