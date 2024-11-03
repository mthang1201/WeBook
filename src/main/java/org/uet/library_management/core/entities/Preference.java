package org.uet.library_management.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Preference.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preference {
    private int userId;

    private String categoryName;

    private int categoryCount;
}
