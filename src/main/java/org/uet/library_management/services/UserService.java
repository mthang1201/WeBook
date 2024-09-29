package org.uet.library_management.services;

import org.uet.library_management.entities.User;
import org.uet.library_management.repositories.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository repository;

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
