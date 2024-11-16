package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.repositories.BookmarkRepository;

import java.util.List;
import java.util.Optional;

/**
 * The BookmarkService class provides methods to manage bookmarks.
 * It interacts with the BookmarkRepository to perform CRUD operations.
 */
public class BookmarkService {
    private final BookmarkRepository repository;

    /**
     * Constructs a new BookmarkService object.
     *
     * Initializes an instance of the BookmarkRepository class which is used
     * for performing CRUD operations on bookmarks.
     */
    public BookmarkService() {
        this.repository = new BookmarkRepository();
    }

    /**
     * Constructs a new BookmarkService object.
     *
     * Initializes an instance of the BookmarkRepository class which is used
     * for performing CRUD operations on bookmarks.
     *
     * @param repository the BookmarkRepository instance to be used for data operations.
     */
    public BookmarkService(BookmarkRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all bookmarks.
     *
     * @return a list of all {@link Bookmark} objects
     */
    public List<Bookmark> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a paginated list of bookmarks.
     *
     * @param page the page number to retrieve, starting from 1
     * @param pageSize the number of bookmarks to retrieve per page
     * @return a list of {@link Bookmark} objects for the specified page
     */
    public List<Bookmark> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    /**
     * Counts all the bookmarks in the repository.
     *
     * @return the total number of bookmarks
     */
    public int countAll() {
        return repository.countAll();
    }

    /**
     * Retrieves a list of bookmarks for a given user and document.
     *
     * @param userId the ID of the user
     * @param isbn13 the ID of the document
     * @return a list of {@link Bookmark} objects associated with the specified user and document
     */
    public Bookmark findById(int userId, String isbn13) {
        return repository.findById(userId, isbn13);
    }

    /**
     * Retrieves a list of bookmarks for a given user.
     *
     * @param userId the ID of the user whose bookmarks are to be retrieved
     * @return a list of {@link Bookmark} objects associated with the specified user
     */
    public List<Bookmark> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Adds a new bookmark to the repository.
     *
     * @param bookmark the Bookmark object containing the user ID and ISBN-13 to be added
     */
    public void add(Bookmark bookmark) {
        repository.add(bookmark);
    }

    /**
     * Updates an existing bookmark in the repository.
     *
     * @param bookmark the Bookmark object containing the updated information. The userId and
     *                 isbn13 fields of the bookmark object will be used to update the repository.
     */
    public void update(Bookmark bookmark) {
        repository.update(bookmark);
    }

    /**
     * Removes a bookmark from the repository.
     *
     * @param bookmark the Bookmark object containing the user ID and ISBN-13 associated with the bookmark to be removed
     */
    public void remove(Bookmark bookmark) {
        repository.remove(bookmark);
    }

    /**
     * Removes all bookmarks from the repository.
     *
     * Delegates to the repository to delete all records, effectively clearing
     * the entire persisted collection of bookmarks. Use this method with caution.
     */
    public void removeAll() {
        repository.removeAll();
    }
}


