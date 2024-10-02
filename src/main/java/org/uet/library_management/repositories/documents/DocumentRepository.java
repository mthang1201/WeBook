package org.uet.library_management.repositories.documents;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.entities.documents.Document;
import org.uet.library_management.entities.documents.Thesis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DocumentRepository {
    protected String db_table;

    private final ConnectJDBC connectJDBC;

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
        } else if (db_table.equals("thesis")) {
            return new Thesis();
        }
        throw new IllegalArgumentException("Unknown db_table: " + db_table);
    }

    private void populateSpecificAttributes(Document document, ResultSet rs) throws SQLException {
        if (document instanceof Book book) {
            book.setPublisher(rs.getString("publisher"));
            book.setIsbn(rs.getString("isbn"));
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

    public List<Document> findAllByPage(int page, int pageSize) {
        return null;
    }

    public int countAll() {
        return 0;
    }

    public List<Document> findByName(String name) {
        return null;
    }

    public List<Document> findByAuthor(String author) {
        return null;
    }

    public void add(Document document) {
    }

    public void update(Document document) {
    }

    public void remove(Document document) {
    }

    public void removeAll() {
    }
}
