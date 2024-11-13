package org.uet.library_management.core.repositories.documents;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.entities.documents.Document;
import org.uet.library_management.core.entities.documents.Thesis;
import org.uet.library_management.core.repositories.MySQLRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Abstract class representing a document repository.
 * Provides common database operations for documents, such as finding,
 * adding, updating, and removing documents.
 *
 * @param <T> a type parameter that extends the Document class.
 */
public abstract class DocumentRepository<T extends Document> implements MySQLRepository<T> {
    protected String db_table;

    protected final ConnectJDBC connectJDBC;

    /**
     * Constructs a new DocumentRepository instance.
     *
     * This constructor initializes the repository by establishing a JDBC connection
     * and loading the database table name. It performs the following actions:
     * 1. Creates a new instance of ConnectJDBC to manage database connections.
     * 2. Calls the loadDatabase() method to set the appropriate table name for the repository.
     */
    public DocumentRepository() {
        connectJDBC = new ConnectJDBC();
        loadDatabase();
    }

    /**
     * Initializes the database table name for the repository.
     *
     * This method sets the table name used by the repository to "documents".
     * It is typically called during the instantiation of the repository to
     * ensure that the correct table is being used for subsequent database operations.
     */
    protected void loadDatabase() {
        db_table = "documents";
    }

    /**
     * Creates a new Document instance based on the value of the `db_table` field.
     *
     * @return a new instance of either Book or Thesis, depending on the value of `db_table`.
     * @throws IllegalArgumentException if `db_table` is not recognized.
     */
    private Document createDocument() {
        if (db_table.equals("books")) {
            return new Book();
        } else if (db_table.equals("theses")) {
            return new Thesis();
        }
        throw new IllegalArgumentException("Unknown db_table: " + db_table);
    }

    /**
     * Populates specific attributes for a given Document type from a ResultSet.
     *
     * @param document the Document to populate. Can be an instance of either Book or Thesis.
     * @param rs       the ResultSet containing the data to populate the document's attributes.
     * @throws SQLException if a database access error occurs or the ResultSet is not properly structured.
     */
    private void populateSpecificAttributes(Document document, ResultSet rs) throws SQLException {
        if (document instanceof Book book) {
            book.setPublisher(rs.getString("publisher"));
            book.setIsbn10(rs.getString("isbn10"));
            book.setIsbn13(rs.getString("isbn13"));
            book.setPageCount(rs.getInt("pageCount"));
            book.setAverageRating(rs.getDouble("averageRating"));
            book.setRatingsCount(rs.getInt("ratingsCount"));
            book.setImageLinks(rs.getString("imageLinks"));
            book.setMaturityRating(rs.getString("maturityRating"));
            book.setPrintType(rs.getString("printType"));
        } else if (document instanceof Thesis thesis) {
            thesis.setInstitution(rs.getString("institution"));
            thesis.setDegree(rs.getString("degree"));
            thesis.setCitationCount(rs.getInt("citationCount"));
        }
    }

    /**
     * Populates a Document object with data from a ResultSet.
     *
     * @param rs the ResultSet containing the data to populate the Document.
     * @return the populated Document object.
     * @throws SQLException if a database access error occurs or the ResultSet is not properly structured.
     */
    protected T populateDocument(ResultSet rs) throws SQLException {
        Document document = createDocument();
        document.setDocumentId(rs.getInt("documentId"));
        document.setTitle(rs.getString("title"));
        document.setAuthors(rs.getString("authors"));
        document.setPublishedDate(rs.getString("publishedDate"));
        document.setDescription(rs.getString("description"));
        document.setCategories(rs.getString("categories"));
        document.setLanguage(rs.getString("language"));

        populateSpecificAttributes(document, rs);
        return (T) document;
    }

    /**
     * Retrieves all documents from the database.
     *
     * @return a list of all documents found in the database.
     * @throws RuntimeException if a database access error occurs.
     */
    @Override
    public List<T> findAll() {
        List<T> documents = new ArrayList<>();
        String query = "SELECT * FROM " + db_table;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while(rs.next()) {
                documents.add(populateDocument(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return documents;
    }

    /**
     * Retrieves a paginated list of all documents from the database.
     *
     * @param page the page number to retrieve, starting from 1
     * @param pageSize the number of documents to retrieve per page
     * @return a list of documents for the specified page and page size
     */
    @Override
    public List<T> findAllByPage(int page, int pageSize) {
        List<T> documents = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while(rs.next()) {
                documents.add(populateDocument(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return documents;
    }

    /**
     * Counts the total number of records in the database table associated with this repository.
     *
     * @return the total count of records in the associated database table
     */
    @Override
    public int countAll() {
        String query = "SELECT COUNT(*) AS total FROM " + db_table;
        int count = 0;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Retrieves a document by its ISBN-13 identifier from the database.
     *
     * @param isbn13 The ISBN-13 identifier of the document to be retrieved.
     * @return An Optional containing the found document if it exists, or an empty Optional if not.
     */
    public Optional<T> findById(String isbn13) {
        T document = null;
        String query = "SELECT * FROM " + db_table + " WHERE isbn13 LIKE ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, isbn13)) {
            while(rs.next()) {
                document = populateDocument(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(document);
    }

    /**
     * Finds and retrieves a list of documents from the database that match the specified title.
     *
     * @param title The title or partial title to search for in the database.
     * @return A list of documents that match the specified title.
     * @throws RuntimeException if a database access error occurs.
     */
    public List<T> findByTitle(String title) {
        List<T> documents = new ArrayList<>();
        String query = "SELECT * FROM " + db_table + " WHERE title LIKE ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, title)) {
            while(rs.next()) {
                documents.add(populateDocument(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return documents;
    }

    /**
     * Finds and retrieves a list of documents from the database that match the specified authors.
     *
     * @param authors The authors or partial authors to search for in the database.
     * @return A list of documents that match the specified authors.
     * @throws RuntimeException if a database access error occurs.
     */
    public List<T> findByAuthors(String authors) {
        List<T> documents = new ArrayList<>();
        String query = "SELECT * FROM " + db_table + " WHERE authors LIKE ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, authors)) {
            while(rs.next()) {
                documents.add(populateDocument(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return documents;
    }

    @Override
    public void add(Document document) {
    }

    @Override
    public void update(Document document) {
    }

    /**
     * Removes the specified document from the database.
     *
     * This method constructs and executes an SQL DELETE statement to remove the
     * document with the specified document ID from the database table.
     *
     * @param document the Document object to be removed from the database.
     */
    @Override
    public void remove(Document document) {
        String query = "DELETE FROM " + db_table + " WHERE documentId = ?";
        connectJDBC.executeUpdate(query, document.getDocumentId());
    }

    /**
     * Removes all records from the database table associated with this repository.
     *
     * This method constructs an SQL DELETE statement to remove all entries from
     * the designated table and executes the statement using the JDBC connection.
     *
     * It does not return any result and should be used with caution as it will
     * permanently delete all data from the specified table.
     */
    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
