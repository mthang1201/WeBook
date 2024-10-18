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

public abstract class DocumentRepository<T extends Document> implements MySQLRepository<T> {
    protected String db_table;

    protected final ConnectJDBC connectJDBC;

    public DocumentRepository() {
        connectJDBC = new ConnectJDBC();
        loadDatabase();
    }

    protected void loadDatabase() {
        db_table = "documents";
    }

    private Document createDocument() {
        if (db_table.equals("books")) {
            return new Book();
        } else if (db_table.equals("theses")) {
            return new Thesis();
        }
        throw new IllegalArgumentException("Unknown db_table: " + db_table);
    }

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

    private T populateDocument(ResultSet rs) throws SQLException {
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

    @Override
    public void remove(Document document) {
        String query = "DELETE FROM " + db_table + " WHERE documentId = ?";
        connectJDBC.executeUpdate(query, document.getDocumentId());
    }

    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
