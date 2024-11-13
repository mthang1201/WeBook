package org.uet.library_management.core.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A class representing a thesis, which is a type of Document.
 * It includes additional attributes specific to theses such as
 * the institution where the thesis was completed, the degree
 * associated with the thesis, and the citation count.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thesis extends Document {
    private String institution;

    private String degree;

    private int citationCount;
}
