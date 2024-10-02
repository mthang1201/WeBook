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
            book.setIsbn(rs.getString("ISBN"));
            book.setPublisher(rs.getString("publisher"));
        } else if (document instanceof Thesis thesis) {
            thesis.setUniversity(rs.getString("university"));
            thesis.setSupervisor(rs.getString("supervisor"));
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
                document.setDocumentId(rs.getString("documentId"));
                document.setDocumentName(rs.getString("documentName"));
                document.setGenre(rs.getString("genre"));
                document.setAuthorName(rs.getString("authorName"));
                document.setDocumentDescription(rs.getString("documentDescription"));
                document.setPublishedDate(rs.getString("publishedDate"));
                document.setQuantityInStock(rs.getInt("quantityInStock"));

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
