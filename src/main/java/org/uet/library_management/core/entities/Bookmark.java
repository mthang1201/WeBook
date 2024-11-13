package org.uet.library_management.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Bookmark class represents a bookmark in the system.
 * It contains information about the user who created the bookmark
 * and the identifier (ISBN-13) of the document that is bookmarked.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark {
    private int userId;

    private String isbn13;
}
