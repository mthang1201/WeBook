package org.uet.library_management.repositories.documents;

public class BookRepository extends DocumentRepository {
    public BookRepository() {
        super();
    }

    @Override
    protected void loadDatabase() {
        db_table = "books";
    }
}
