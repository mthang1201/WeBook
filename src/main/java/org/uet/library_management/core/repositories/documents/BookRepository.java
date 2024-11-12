package org.uet.library_management.core.repositories.documents;

import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.entities.documents.Document;
import org.uet.library_management.tools.SessionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        if (findById(book.getIsbn13()).isPresent()) return;

        String query = "INSERT INTO " + db_table + " (title, authors, publisher, " +
                "publishedDate, description, isbn10, isbn13, pageCount, categories, averageRating, " +
                "ratingsCount, imageLinks, language, maturityRating, printType) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                book.getPublisher(), document.getPublishedDate(), document.getDescription(),
                book.getIsbn10(), book.getIsbn13(), book.getPageCount(), document.getCategories(),
                book.getAverageRating(), book.getRatingsCount(), book.getImageLinks(),
                document.getLanguage(), book.getMaturityRating(), book.getPrintType());
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
                "publishedDate = ?, description = ?, isbn10 = ?, isbn13 = ?, pageCount = ?, " +
                "categories = ?, averageRating = ?, ratingsCount = ?, imageLinks = ?, " +
                "language = ?, maturityRating = ?, printType = ? " +
                "WHERE isbn13 = ?";
        connectJDBC.executeUpdate(query,
                document.getTitle(),
                document.getAuthors(),
                book.getPublisher(),
                document.getPublishedDate(),
                document.getDescription(),
                book.getIsbn10(),
                book.getIsbn13(),
                book.getPageCount(),
                document.getCategories(),
                book.getAverageRating(),
                book.getRatingsCount(),
                book.getImageLinks(),
                document.getLanguage(),
                book.getMaturityRating(),
                book.getPrintType(),
                book.getIsbn13());
    }

    public List<Book> getTopRatedSearchTermBooks(double minRating, String searchTerm) {
        String query = "SELECT b.* FROM " + db_table + " b " +
                "JOIN documentEvaluations de ON b.isbn13 = de.isbn13 " +
                "WHERE b.title LIKE ? OR b.authors LIKE ? OR b.description LIKE ? OR b.categories LIKE ? " +
                "GROUP BY b.isbn13 " +
                "HAVING AVG(de.rating) >= ?";

        // Add wildcards to the search term for partial matching
        String searchTermWithWildcards = "%" + searchTerm + "%";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query,
                searchTermWithWildcards,
                searchTermWithWildcards,
                searchTermWithWildcards,
                searchTermWithWildcards,
                minRating)) {
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(populateDocument(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getUpdatedAverageRating(String isbn13) {

        String query = "SELECT AVG(rating) FROM documentEvaluations WHERE isbn13 = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, isbn13)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    public List<Book> getBooksFromLoans() {
        List<Book> books = new ArrayList<>();
        String query = "select b.documentId, b.title, b.authors, b.publishedDate, b.description, b.categories, b.language, " +
                "b.publisher, b.isbn10, b.isbn13, b.pageCount, b.averageRating, b.ratingsCount, b.imageLinks, b.maturityRating, " +
                "b.printType " +
                "from books b " +
                "inner join loans l " +
                "on b.isbn13 = l.isbn13 " +
                "where userId = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, SessionManager.user.getUserId())) {
            while (rs.next()) {
                books.add(populateDocument(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public List<Book> getBooksFromBookmarks() {
        List<Book> books = new ArrayList<>();
        String query = "select b.documentId, b.title, b.authors, b.publishedDate, b.description, b.categories, b.language, " +
                "b.publisher, b.isbn10, b.isbn13, b.pageCount, b.averageRating, b.ratingsCount, b.imageLinks, b.maturityRating, " +
                "b.printType " +
                "from books b " +
                "inner join bookmarks bm " +
                "on b.isbn13 = bm.isbn13 " +
                "where userId = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, SessionManager.user.getUserId())) {
            while (rs.next()) {
                books.add(populateDocument(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }
}
