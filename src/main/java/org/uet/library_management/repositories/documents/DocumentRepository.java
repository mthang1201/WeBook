package org.uet.library_management.repositories.documents;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.entities.documents.Document;
import org.uet.library_management.entities.documents.Thesis;
import org.uet.library_management.repositories.MySQLRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DocumentRepository implements MySQLRepository<Document> {
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
            book.setIsbn10(rs.getString("isbn"));
            book.setIsbn13(rs.getString("isbn"));
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

    @Override
    public List<Document> findAll() {
        List<Document> documents = new ArrayList<>();

        String query = "SELECT * FROM " + db_table;
        ResultSet rs = connectJDBC.executeQuery(query);
        while (true) {
            try {
                if (!rs.next()) break;
                Document document = createDocument();
                document.setDocumentId(rs.getInt("documentId"));
                document.setTitle(rs.getString("title"));
                document.setAuthors(rs.getString("authors"));
                document.setPublishedDate(String.valueOf(rs.getDate("publishedDate")));
                document.setDescription(rs.getString("description"));
                document.setCategories(rs.getString("categories"));
                document.setLanguage(rs.getString("language"));
                document.setAvailableCopies(rs.getInt("availableCopies"));

                populateSpecificAttributes(document, rs);

                documents.add(document);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return documents;
    }

    @Override
    public List<Document> findAllByPage(int page, int pageSize) {
        List<Document> documents = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;
        ResultSet rs = connectJDBC.executeQuery(query);
        while (true) {
            try {
                if (!rs.next()) break;
                Document document = createDocument();
                document.setDocumentId(rs.getInt("documentId"));
                document.setTitle(rs.getString("title"));
                document.setAuthors(rs.getString("authors"));
                document.setPublishedDate(String.valueOf(rs.getDate("publishedDate")));
                document.setDescription(rs.getString("description"));
                document.setCategories(rs.getString("categories"));
                document.setLanguage(rs.getString("language"));
                document.setAvailableCopies(rs.getInt("availableCopies"));

                populateSpecificAttributes(document, rs);

                documents.add(document);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    public List<Document> findByTitle(String title) {
        List<Document> documents = new ArrayList<>();

        String query = "SELECT * FROM " + db_table + " WHERE title LIKE ?";
        ResultSet rs = connectJDBC.executeQueryWithParams(query);
        while (true) {
            try {
                if (!rs.next()) break;
                Document document = createDocument();
                document.setDocumentId(rs.getInt("documentId"));
                document.setTitle(rs.getString("title"));
                document.setAuthors(rs.getString("authors"));
                document.setPublishedDate(String.valueOf(rs.getDate("publishedDate")));
                document.setDescription(rs.getString("description"));
                document.setCategories(rs.getString("categories"));
                document.setLanguage(rs.getString("language"));
                document.setAvailableCopies(rs.getInt("availableCopies"));

                populateSpecificAttributes(document, rs);

                documents.add(document);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return documents;
    }

    public List<Document> findByAuthors(String authors) {
        List<Document> documents = new ArrayList<>();

        String query = "SELECT * FROM " + db_table + " WHERE authors LIKE ?";
        ResultSet rs = connectJDBC.executeQueryWithParams(query);
        while (true) {
            try {
                if (!rs.next()) break;
                Document document = createDocument();
                document.setDocumentId(rs.getInt("documentId"));
                document.setTitle(rs.getString("title"));
                document.setAuthors(rs.getString("authors"));
                document.setPublishedDate(String.valueOf(rs.getDate("publishedDate")));
                document.setDescription(rs.getString("description"));
                document.setCategories(rs.getString("categories"));
                document.setLanguage(rs.getString("language"));
                document.setAvailableCopies(rs.getInt("availableCopies"));

                populateSpecificAttributes(document, rs);

                documents.add(document);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return documents;
    }

    @Override
    public void add(Document document) {
        String query = "INSERT INTO " + db_table + " (title, authors, publishedDate, description, categories, language, availableCopies) VALUES (?, ?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                document.getPublishedDate(), document.getDescription(),
                document.getCategories(), document.getLanguage(),
                document.getAvailableCopies());
    }

    @Override
    public void update(Document document) {
        String query = "UPDATE " + db_table + " SET title = ?, authors = ?, publishedDate = ?, description = ?, categories = ?, language = ?, availableCopies = ? WHERE documentId = ?";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                document.getPublishedDate(), document.getDescription(),
                document.getCategories(), document.getLanguage(),
                document.getAvailableCopies(), document.getDocumentId());
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
