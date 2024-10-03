package org.uet.library_management.repositories.documents;

import org.uet.library_management.entities.documents.Thesis;
import org.uet.library_management.entities.documents.Document;

public class ThesisRepository extends DocumentRepository {
    public ThesisRepository() {
        super();
    }

    @Override
    protected void loadDatabase() {
        db_table = "theses";
    }

    @Override
    public void add(Document document) {
        Thesis thesis = (Thesis) document;
        String query = "INSERT INTO " + db_table + " (title, authors, publishedDate, " +
                "institution, degree, description, categories, language, citationCount, " +
                "availableCopies) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                document.getPublishedDate(), thesis.getInstitution(), thesis.getDegree(),
                document.getDescription(), document.getCategories(), document.getLanguage(),
                thesis.getCitationCount(), document.getAvailableCopies());
    }

    @Override
    public void update(Document document) {
        Thesis thesis = (Thesis) document;
        String query = "UPDATE " + db_table + " SET title = ?, authors = ?, publishedDate = ?, " +
                "institution = ?, degree = ?, description = ?, categories = ?, language = ?, " +
                "citationCount = ?, availableCopies = ? WHERE documentId = ?";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                document.getPublishedDate(), thesis.getInstitution(), thesis.getDegree(),
                document.getDescription(), document.getCategories(), document.getLanguage(),
                thesis.getCitationCount(), document.getAvailableCopies(),
                document.getDocumentId());
    }
}
