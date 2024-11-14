package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.Preference;
import org.uet.library_management.core.repositories.PreferenceRepository;

import java.util.List;
import java.util.Optional;

/**
 * Handles user preferences by interacting with the PreferenceRepository.
 * Supports adding, retrieving, updating, and removing user preferences.
 */
public class PreferenceService {
    private final PreferenceRepository repository;

    /**
     * Constructs a new PreferenceService that initializes a new PreferenceRepository.
     */
    public PreferenceService() {
        this.repository = new PreferenceRepository();
    }

    /**
     * Adds preferences for a user by updating or inserting category counts.
     *
     * @param userId the ID of the user for whom the preferences are being added
     * @param categoryNames a list of category names to be added or updated for the user
     */
    public void addPreferenceForUser(int userId, List<String> categoryNames) {
        repository.addPreferenceForUser(userId, categoryNames);
    }

    /**
     * Retrieves the count of a specific category for a given user.
     *
     * @param userId the ID of the user whose category count is to be retrieved
     * @param categoryName the name of the category for which the count is to be retrieved
     * @return the count of the specified category for the given user
     */
    public int getCategoryCount(int userId, String categoryName) {
        return repository.getCategoryCount(userId, categoryName);
    }

    /**
     * Retrieves the category with the highest count for a given user.
     *
     * @param userId the ID of the user for whom the maximum category is to be retrieved
     * @return the name of the category with the highest count for the given user, or null if no category is found
     */
    public String getMaxCategory(int userId) {
        return repository.getMaxCategory(userId);
    }

    /**
     * Retrieves all user preferences.
     *
     * @return a list of all user preferences.
     */
    public List<Preference> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a paginated list of user preferences.
     *
     * @param page the number of the page to retrieve
     * @param pageSize the number of preferences per page
     * @return a list of user preferences for the specified page
     */
    public List<Preference> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    /**
     * Counts all user preferences.
     *
     * @return the total number of user preferences
     */
    public int countAll() {
        return repository.countAll();
    }

    /**
     * Finds and retrieves a list of preferences that match the given category name.
     *
     * @param name the category name to search for within the preferences.
     * @return a list of Preference objects that match the specified category name.
     */
    public List<Preference> findByName(String name) {
        return repository.findByName(name);
    }

    /**
     * Finds a user's preference by their user ID.
     *
     * @param userId the ID of the user whose preference is to be retrieved
     * @return an Optional containing the user's preference if found, or an empty Optional if no preference is found
     */
    public Optional<Preference> findByEmail(int userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Adds a user preference to the repository.
     *
     * @param preference the Preference object containing the user ID, category name, and category count
     */
    public void add(Preference preference) {
        repository.add(preference);
    }

    /**
     * Updates an existing user preference with the given preference data.
     *
     * @param preference the Preference object containing the updated details such as user ID, category name, and category count.
     */
    public void update(Preference preference) {
        repository.update(preference);
    }

    /**
     * Removes a specific user preference from the repository.
     *
     * @param preference the Preference object containing the user ID and category name to be removed
     */
    public void remove(Preference preference) {
        repository.remove(preference);
    }

    /**
     * Removes all user preferences from the repository.
     * This method calls the underlying repository's removeAll method to delete all records.
     */
    public void removeAll() {
        repository.removeAll();
    }
}

