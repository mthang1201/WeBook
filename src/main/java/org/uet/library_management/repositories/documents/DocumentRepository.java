package org.uet.library_management.repositories.documents;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.entities.documents.Document;

import java.util.List;

public class DocumentRepository {
    protected String db_table;

    private final ConnectJDBC connectJDBC;

    public DocumentRepository() {
        connectJDBC = new ConnectJDBC();
        loadDatabase();
    }

    protected void loadDatabase() {
        db_table = "documents";
    }

    public List<Document> findAll() {
        return null;
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
