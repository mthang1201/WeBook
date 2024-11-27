package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * The UserService class provides various methods to manage users in the system.
 * It uses a UserRepository object to interact with the data source.
 */
public class UserService {
    private final UserRepository repository;

    /**
     * Constructs a new UserService object.
     *
     * This constructor initializes the repository field with a new instance
     * of UserRepository. The UserRepository object is used to perform CRUD
     * operations on user data within the system.
     */
    public UserService() {
        this.repository = new UserRepository();
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return a list of all users.
     */
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a list of users for the specified page in a paginated format.
     *
     * @param page the page number to retrieve
     * @param pageSize the number of users per page
     * @return a list of users for the specified page
     */
    public List<User> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    /**
     * Counts the total number of users in the repository.
     *
     * @return the total count of users
     */
    public int countAll() {
        return repository.countAll();
    }

    /**
     * Retrieves a list of users whose names match the given name pattern.
     *
     * @param name the name pattern to match against usernames in the database.
     * @return a list of users whose names match the provided pattern.
     */
    public List<User> findByName(String name) {
        return repository.findByName(name);
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user to find.
     * @return an Optional containing the found User, or an empty Optional if no user was found.
     */
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    /**
     * Adds a new user to the repository.
     *
     * @param user the User object containing the data to be added to the repository.
     */
    public void add(User user) {
        repository.add(user);
    }

    /**
     * Updates the information of an existing user in the repository.
     *
     * @param user the User object containing updated information to be stored in the repository.
     */
    public void update(User user) {
        repository.update(user);
    }

    public void updateForm(User user) {
        repository.updateForm(user);
    }

    /**
     * Removes the specified user from the repository.
     *
     * @param user the User object to be removed from the repository.
     */
    public void remove(User user) {
        repository.remove(user);
    }

    /**
     * Removes all users from the repository.
     *
     * This method will delete all user records from the data source
     * by invoking the removeAll method of the UserRepository class.
     *
     * @throws RuntimeException if a database access error occurs
     *                          while executing this operation in the repository.
     */
    public void removeAll() {
        repository.removeAll();
    }
}
