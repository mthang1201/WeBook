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

    public DocumentService() {
        loadRepository();
    }

    protected void loadRepository() {
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public List<T> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    public int countAll() {
        return repository.countAll();
    }

    public Optional<T> findById(String isbn13) {
        return repository.findById(isbn13);
    }

    public List<T> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public List<T> findByAuthor(String authors) {
        return repository.findByAuthors(authors);
    }

    public void add(Document document) {
        repository.add(document);
    }

    public void update(Document document) {
        repository.update(document);
    }

    public void remove(Document document) {
        repository.remove(document);
    }

    public void removeAll() {
        repository.removeAll();
    }
}
