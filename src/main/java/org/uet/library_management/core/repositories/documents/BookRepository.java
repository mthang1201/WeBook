package org.uet.library_management.core.repositories.documents;

import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.entities.documents.Document;
import org.uet.library_management.tools.SessionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The BookRepository class is responsible for interacting with the books table in the database.
 * It extends the DocumentRepository class and provides specific implementations for
 * handling Book entities.
 */
public class BookRepository extends DocumentRepository<Book> {
    public BookRepository() {
        super();
    }

    /**
     * Overrides the loadDatabase method to set the database table to "books".
     * This method is called during the initialization of the BookRepository to
     * ensure it interacts with the correct database table specific to book entities.
     */
    @Override
    protected void loadDatabase() {
        db_table = "books";
    }

    /**
     * Adds a new Document to the repository. If the document is an instance of Book,
     * it inserts the book's properties into the database table if the book does not already exist.
     *
     * @param document The Document to be added. This should be an instance of Book.
     */
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

    /**
     * Updates the details of an existing book in the database.
     * This method takes a Document object, casts it to a Book, and updates the
     * book's attributes in the database table identified by `db_table`,
     * with a match on the book's ISBN-13.
     *
     * @param document The Document to be updated. This should be an instance of Book.
     */
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

    /**
     * Retrieves a list of Books that have been bookmarked by the user.
     * This method executes a SQL query to fetch books from the bookmarks table,
     * joins the books and bookmarks tables based on the ISBN-13 value,
     * and groups the results by ISBN-13 and title.
     *
     * @return a list of Book objects representing the bookmarks, or an empty list if no bookmarks are found.
     * @throws RuntimeException if a database access error occurs.
     */
    public List<Book> getRandomTitlesFromBookmarks() {
        String query = "SELECT b.* " +
                "FROM " + db_table + " b " +
                "JOIN bookmarks bm ON b.isbn13 = bm.isbn13 " +
                "GROUP BY b.isbn13, b.title;";

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(populateDocument(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of books that match a given search term and have an average rating
     * equal to or greater than the specified minimum rating.
     *
     * @param minRating The minimum average rating a book must have to be included in the results.
     * @param searchTerm The search term to filter books by title, authors, description, or categories.
     * @return A list of books that match the search criteria and have an average rating meeting the minimum threshold.
     */
    public List<Book> getTopRatedSearchTermBooks(double minRating, String searchTerm) {
        String query = "SELECT b.* FROM " + db_table + " b " +
                "JOIN documentEvaluations de ON b.isbn13 = de.isbn13 " +
                "WHERE b.title LIKE ? OR b.authors LIKE ? OR b.description LIKE ? OR b.categories LIKE ? " +
                "GROUP BY b.isbn13 " +
                "HAVING AVG(de.rating) >= ?";

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

    /**
     * Retrieves the updated average rating for a book specified by its ISBN-13.
     *
     * @param isbn13 The ISBN-13 identifier of the book.
     * @return The updated average rating of the book, or 0.0 if no ratings are found.
     */
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

    /**
     * Retrieves a list of books that have been borrowed by the current user.
     * Executes a query that joins the books and loans tables based on the ISBN-13 value,
     * filtered by the user ID from the current session.
     *
     * @return a list of Book objects representing the borrowed books.
     */
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

    /**
     * Retrieves a list of books that have been bookmarked by the current user.
     * Executes a query that joins the books and bookmarks tables based on the ISBN-13 value,
     * filtered by the user ID from the current session.
     *
     * @return a list of Book objects representing the bookmarked books.
     */
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
