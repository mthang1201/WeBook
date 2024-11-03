package org.uet.library_management.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bookmark.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark {
    private int userId;

    private String isbn13;
}
