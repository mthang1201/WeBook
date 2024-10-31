package org.uet.library_management.core.repositories.documents;

import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.entities.documents.Document;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkRepository extends BookRepository {
    public BookmarkRepository() {
        super();
    }

    @Override
    protected void loadDatabase() {
        db_table = "books";
    }
}
