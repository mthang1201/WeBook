package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.DocumentEvaluation;
import org.uet.library_management.core.repositories.DocumentEvaluationRepository;

import java.util.List;

public class DocumentEvaluationService {
    private final DocumentEvaluationRepository repository;

    public DocumentEvaluationService() {
        this.repository = new DocumentEvaluationRepository();
    }

    public DocumentEvaluation getUserReview(String isbn13, int userId) {
        return repository.getUserReview(isbn13, userId);
    }

    public boolean hasEvaluated(String isbn13, int userId) {
        return repository.hasEvaluated(isbn13, userId);
    }

    public double getAvgRatings(String isbn13) {
        return repository.getAvgRatings(isbn13);
    }


    public List<DocumentEvaluation> findAll() {
        return repository.findAll();
    }

    public List<DocumentEvaluation> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    public int countAll() {
        return repository.countAll();
    }

    public List<DocumentEvaluation> findByIsbn13(String isbn13) {
        return repository.findByIsbn13(isbn13);
    }

    public List<DocumentEvaluation> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public void add(DocumentEvaluation evaluation) {
        repository.add(evaluation);
    }

    public void update(DocumentEvaluation evaluation) {
        repository.update(evaluation);
    }

    public void remove(DocumentEvaluation evaluation) {
        repository.remove(evaluation);
    }

    public void removeAll() {
        repository.removeAll();
    }
}
