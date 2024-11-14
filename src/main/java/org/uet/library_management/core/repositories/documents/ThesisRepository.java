package org.uet.library_management.core.repositories.documents;

import lombok.Getter;
import lombok.Setter;
import org.uet.library_management.core.entities.documents.Thesis;
import org.uet.library_management.core.entities.documents.Document;

/**
 * ThesisRepository is a specialized repository for handling Thesis documents.
 * It extends the DocumentRepository class with operations specific to Thesis entities.
 */
public class ThesisRepository extends DocumentRepository<Thesis> {
    public ThesisRepository() {
        super();
    }

    @Override
    protected void loadDatabase() {
        db_table = "theses";
    }

    /**
     * Adds a Thesis document to the repository.
     *
     * @param document the document to be added, which must be an instance of Thesis.
     */
    @Override
    public void add(Document document) {
        Thesis thesis = (Thesis) document;
        String query = "INSERT INTO " + db_table + " (title, authors, publishedDate, " +
                "institution, degree, description, categories, language, citationCount, " +
                "availableCopies) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                document.getPublishedDate(), thesis.getInstitution(), thesis.getDegree(),
                document.getDescription(), document.getCategories(), document.getLanguage(),
                thesis.getCitationCount());
    }

    /**
     * Updates an existing Thesis document in the repository.
     *
     * @param document the document to be updated, which must be an instance of Thesis.
     */
    @Override
    public void update(Document document) {
        Thesis thesis = (Thesis) document;
        String query = "UPDATE " + db_table + " SET title = ?, authors = ?, publishedDate = ?, " +
                "institution = ?, degree = ?, description = ?, categories = ?, language = ?, " +
                "citationCount = ?, availableCopies = ? WHERE documentId = ?";
        connectJDBC.executeUpdate(query, document.getTitle(), document.getAuthors(),
                document.getPublishedDate(), thesis.getInstitution(), thesis.getDegree(),
                document.getDescription(), document.getCategories(), document.getLanguage(),
                thesis.getCitationCount(), document.getDocumentId());
    }
}
