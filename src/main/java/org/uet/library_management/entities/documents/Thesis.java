package org.uet.library_management.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thesis extends Document {
    private String institution;

    private String degree;

    private int citationCount;
}
