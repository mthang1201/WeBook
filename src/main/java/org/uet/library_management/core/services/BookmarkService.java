package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.repositories.BookmarkRepository;

import java.util.List;
import java.util.Optional;

public class BookmarkService {
    private final BookmarkRepository repository;

    public BookmarkService() {
        this.repository = new BookmarkRepository();
    }

    public List<Bookmark> findAll() {
        return repository.findAll();
    }

    public List<Bookmark> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    public int countAll() {
        return repository.countAll();
    }

    public List<Bookmark> findById(int userId, int documentId) {
        return repository.findById(userId, documentId);
    }

    public List<Bookmark> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public void add(Bookmark bookmark) {
        repository.add(bookmark);
    }

    public void update(Bookmark bookmark) {
        repository.update(bookmark);
    }

    public void remove(Bookmark bookmark) {
        repository.remove(bookmark);
    }

    public void removeAll() {
        repository.removeAll();
    }
}


