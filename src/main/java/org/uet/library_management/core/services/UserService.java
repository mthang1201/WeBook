package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository repository;

    public UserService() {
        this.repository = new UserRepository();
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public List<User> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    public int countAll() {
        return repository.countAll();
    }

    public List<User> findByName(String name) {
        return repository.findByName(name);
    }

    public void add(User user) {
        repository.add(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void remove(User user) {
        repository.remove(user);
    }

    public void removeAll() {
        repository.removeAll();
    }
}
