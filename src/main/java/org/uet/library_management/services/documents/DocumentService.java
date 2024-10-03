package org.uet.library_management.services.documents;

import org.uet.library_management.entities.documents.Document;
import org.uet.library_management.repositories.documents.DocumentRepository;

import java.util.List;

public class DocumentService {
    protected DocumentRepository repository;

    public DocumentService() {
        loadRepository();
    }

    protected void loadRepository() {
    }

    public List<Document> findAll() {
        return repository.findAll();
    }

    public List<Document> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    public int countAll() {
        return repository.countAll();
    }

    public List<Document> findByName(String title) {
        return repository.findByTitle(title);
    }

    public List<Document> findByAuthor(String authors) {
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
