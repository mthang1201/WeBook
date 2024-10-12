package org.uet.library_management.services.api.search;

import com.google.api.services.books.v1.model.Volume;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.services.api.image.ImageURLContext;
import org.uet.library_management.services.api.image.NormalThumbnail;
import org.uet.library_management.services.api.image.SmallThumbnail;

import java.util.List;

public class BookDetailsExtractor {
    public static Book extractBookDetails (Volume volume) {
        //solve authors//
        List<String> authors = volume.getVolumeInfo().getAuthors();
        String str_authors = String.join(", ", authors);

        //solve categories//
        List<String> categories = volume.getVolumeInfo().getCategories();
        String str_categories = String.join(", ", categories);

        //solve isbn//
        String isbn_10 = null;
        String isbn_13 = null;
        if (volume.getVolumeInfo().getIndustryIdentifiers() != null) {
            for (Volume.VolumeInfo.IndustryIdentifiers identifier : volume.getVolumeInfo().getIndustryIdentifiers()) {
                if ("ISBN_10".equals(identifier.getType())) {
                    isbn_10 = identifier.getIdentifier();
                } else if ("ISBN_13".equals(identifier.getType())) {
                    isbn_13 = identifier.getIdentifier();
                }
            }
        }

        //solve imgLinks//
        ImageURLContext imageURLContext = new ImageURLContext();
        imageURLContext.setImageURLGenerator(new NormalThumbnail(volume));
        if (imageURLContext.getImageURL() == null) {
            imageURLContext.setImageURLGenerator(new SmallThumbnail(volume));
        }
        Book newBook = new Book(
                volume.getVolumeInfo().getTitle(),
                str_authors,
                volume.getVolumeInfo().getPublisher(),
                volume.getVolumeInfo().getPublishedDate(),
                volume.getVolumeInfo().getDescription(),
                str_categories,
                volume.getVolumeInfo().getPageCount(),
                volume.getVolumeInfo().getAverageRating(),
                volume.getVolumeInfo().getMaturityRating(),
                volume.getVolumeInfo().getPrintType(),
                volume.getVolumeInfo().getLanguage(),
                isbn_10,
                isbn_13,
                imageURLContext.getImageURL());
        return newBook;
    }
}
