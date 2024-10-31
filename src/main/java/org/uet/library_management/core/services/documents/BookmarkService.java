package org.uet.library_management.core.services.documents;

import org.uet.library_management.core.repositories.documents.BookmarkRepository;

import java.util.Arrays;
import java.util.List;

public class BookmarkService extends BookService {
    public BookmarkService() {
        super();
    }

    @Override
    protected void loadRepository() {
        repository = new BookmarkRepository();
    }
}
