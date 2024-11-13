package org.uet.library_management.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Preference class represents a user preference for a particular category.
 * It stores the user's identifier, the category name, and the count of how many times the user has interacted with or selected the category.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preference {
    private int userId;

    private String categoryName;

    private int categoryCount;
}
