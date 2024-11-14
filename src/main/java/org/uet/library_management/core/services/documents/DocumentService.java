package org.uet.library_management.core.services.documents;

import org.uet.library_management.core.entities.documents.Document;
import org.uet.library_management.core.repositories.documents.DocumentRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling operations related to documents.
 *
 * @param <T> The type of Document being managed by this service.
 */
public class DocumentService<T extends Document> {
    protected DocumentRepository repository;

    /**
     * Constructs a new instance of the DocumentService class and initializes
     * the repository by calling the loadRepository method.
     */
    public DocumentService() {
        loadRepository();
    }

    protected void loadRepository() {
    }

    /**
     * Retrieves all documents managed by the service.
     *
     * @return a list of all documents found in the repository.
     */
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a paginated list of documents.
     *
     * @param page the page number to retrieve, starting from 1
     * @param pageSize the number of documents to retrieve per page
     * @return a list of documents for the specified page and page size
     */
    public List<T> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    /**
     * Counts the total number of documents managed by the service.
     *
     * @return the total count of documents in the repository
     */
    public int countAll() {
        return repository.countAll();
    }

    /**
     * Retrieves a single document by its ISBN-13 identifier.
     *
     * @param isbn13 The ISBN-13 identifier of the document to be retrieved.
     * @return An Optional containing the found document if it exists, or an empty Optional if not.
     */
    public Optional<T> findById(String isbn13) {
        return repository.findById(isbn13);
    }

    /**
     * Finds and retrieves a list of documents that match the specified title.
     *
     * @param title The title or partial title to search for in the repository.
     * @return A list of documents that match the specified title.
     */
    public List<T> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    /**
     * Finds and retrieves a list of documents that match the specified authors.
     *
     * @param authors The authors or partial authors to search for in the repository.
     * @return A list of documents that match the specified authors.
     */
    public List<T> findByAuthor(String authors) {
        return repository.findByAuthors(authors);
    }

    /**
     * Adds a new document to the repository.
     *
     * @param document The document to be added.
     */
    public void add(Document document) {
        repository.add(document);
    }

    /**
     * Updates an existing document in the repository.
     *
     * @param document The document to be updated.
     */
    public void update(Document document) {
        repository.update(document);
    }

    /**
     * Removes a document from the repository.
     *
     * @param document the Document object to be removed from the repository.
     */
    public void remove(Document document) {
        repository.remove(document);
    }

    /**
     * Removes all documents from the repository.
     *
     * This method invokes the removeAll method on the repository, which
     * deletes all the records from the database table associated with the repository.
     *
     * Use this method with caution as it will permanently delete all documents
     * from the repository.
     */
    public void removeAll() {
        repository.removeAll();
    }
}
