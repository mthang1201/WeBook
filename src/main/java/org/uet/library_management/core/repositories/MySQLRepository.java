package org.uet.library_management.core.repositories;

import java.util.List;

/**
 * MySQLRepository is an interface providing basic CRUD operations for entities
 * that are to be persisted in a MySQL database.
 *
 * @param <T> the type of the entity being managed by this repository.
 */
public interface MySQLRepository<T> {
    public List<T> findAll();

    public List<T> findAllByPage(int page, int pageSize);

    public int countAll();

    public void add(T entity);

    public void update(T entity);

    public void remove(T entity);

    public void removeAll();
}
