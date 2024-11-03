package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.Preference;
import org.uet.library_management.core.repositories.PreferenceRepository;

import java.util.List;
import java.util.Optional;

public class PreferenceService {
    private final PreferenceRepository repository;

    public PreferenceService() {
        this.repository = new PreferenceRepository();
    }

    public void addPreferenceForUser(int userId, List<String> categoryNames) {
        repository.addPreferenceForUser(userId, categoryNames);
    }

    public int getCategoryCount(int userId, String categoryName) {
        return repository.getCategoryCount(userId, categoryName);
    }

    public String getMaxCategory(int userId) {
        return repository.getMaxCategory(userId);
    }

    public List<Preference> findAll() {
        return repository.findAll();
    }

    public List<Preference> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    public int countAll() {
        return repository.countAll();
    }

    public List<Preference> findByName(String name) {
        return repository.findByName(name);
    }

    public Optional<Preference> findByEmail(int userId) {
        return repository.findByUserId(userId);
    }

    public void add(Preference preference) {
        repository.add(preference);
    }

    public void update(Preference preference) {
        repository.update(preference);
    }

    public void remove(Preference preference) {
        repository.remove(preference);
    }

    public void removeAll() {
        repository.removeAll();
    }
}

