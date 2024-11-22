package org.uet.library_management.api.search;

import com.google.api.services.books.v1.model.Volume;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.api.image.ImageURLContext;
import org.uet.library_management.api.image.NormalThumbnail;
import org.uet.library_management.api.image.SmallThumbnail;

import java.util.List;

/**
 * The BookDetailsExtractor class is responsible for extracting detailed information from a
 * Volume object and converting it into a Book object. This includes authors, categories,
 * ISBNs, image links, and other metadata about the book.
 */
public class BookDetailsExtractor {

    /**
     * Extracts the details of a book from a given Volume object and returns a corresponding Book instance.
     *
     * @param volume The Volume object containing the book's information.
     * @return A Book instance populated with the details extracted from the Volume object.
     */
    public static Book extractBookDetails(Volume volume) {
        // Solve authors
        List<String> authors = volume.getVolumeInfo().getAuthors();
        String strAuthors = (authors != null && !authors.isEmpty())
                ? String.join(", ", authors)
                : "Không có tác giả";

        // Solve categories
        List<String> categories = volume.getVolumeInfo().getCategories();
        String strCategories = (categories != null && !categories.isEmpty())
                ? String.join(", ", categories)
                : "Không có thể loại";

        // Solve ISBN
        String isbn10 = null;
        String isbn13 = null;
        if (volume.getVolumeInfo().getIndustryIdentifiers() != null) {
            for (Volume.VolumeInfo.IndustryIdentifiers identifier : volume.getVolumeInfo().getIndustryIdentifiers()) {
                if ("ISBN_10".equals(identifier.getType())) {
                    isbn10 = identifier.getIdentifier();
                } else if ("ISBN_13".equals(identifier.getType())) {
                    isbn13 = identifier.getIdentifier();
                }
            }
        }

        // Solve image links
        ImageURLContext imageURLContext = new ImageURLContext();
        imageURLContext.setImageURLGenerator(new NormalThumbnail(volume));
        if (imageURLContext.getImageURL() == null) {
            imageURLContext.setImageURLGenerator(new SmallThumbnail(volume));
        }

        // Extract other fields
        String title = volume.getVolumeInfo().getTitle() != null
                ? volume.getVolumeInfo().getTitle()
                : "Không có tiêu đề";
        String publisher = volume.getVolumeInfo().getPublisher() != null
                ? volume.getVolumeInfo().getPublisher()
                : "Không có nhà xuất bản";
        String publishedDate = volume.getVolumeInfo().getPublishedDate() != null
                ? volume.getVolumeInfo().getPublishedDate()
                : "Không có ngày xuất bản";
        String description = volume.getVolumeInfo().getDescription() != null
                ? volume.getVolumeInfo().getDescription()
                : "Không có mô tả";
        int pageCount = volume.getVolumeInfo().getPageCount() != null
                ? volume.getVolumeInfo().getPageCount()
                : 0;
        int ratingsCount = volume.getVolumeInfo().getRatingsCount() != null
                ? volume.getVolumeInfo().getRatingsCount()
                : 0;
        double averageRating = volume.getVolumeInfo().getAverageRating() != null
                ? volume.getVolumeInfo().getAverageRating()
                : 0.0;
        String maturityRating = volume.getVolumeInfo().getMaturityRating() != null
                ? volume.getVolumeInfo().getMaturityRating()
                : "Không có đánh giá trưởng thành";
        String printType = volume.getVolumeInfo().getPrintType() != null
                ? volume.getVolumeInfo().getPrintType()
                : "Không có loại in";
        String language = volume.getVolumeInfo().getLanguage() != null
                ? volume.getVolumeInfo().getLanguage()
                : "Không có ngôn ngữ";
        String thumbnailUrl = imageURLContext.getImageURL() + "&fife=w800&format=webp";

        return new Book(
                title,
                strAuthors,
                publisher,
                publishedDate,
                description,
                strCategories,
                ratingsCount,
                pageCount,
                averageRating,
                maturityRating,
                printType,
                language,
                isbn10,
                isbn13,
                thumbnailUrl
        );
    }
}
