package org.uet.library_management.entities.documents;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Document {
    private String isbn;

    private String publisher;
}
