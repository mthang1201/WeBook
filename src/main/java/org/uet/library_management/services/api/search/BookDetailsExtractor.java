package org.uet.library_management.services.api.search;

import com.google.api.services.books.v1.model.Volume;
import org.uet.library_management.entities.documents.Book;
import org.uet.library_management.services.api.image.*;

import java.util.List;

public class BookDetailsExtractor {
    public static Book extractBookDetails (Volume volume) {
        //solve authors//
        List<String> authors = volume.getVolumeInfo().getAuthors();
        String str_authors = (authors != null && !authors.isEmpty()) ? String.join(", ", authors) : "Không có tác giả";

        //solve categories//
        List<String> categories = volume.getVolumeInfo().getCategories();
        String str_categories = (categories != null && !categories.isEmpty()) ? String.join(", ", categories) : "Không có thể loại";
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
        imageURLContext.setImageURLGenerator(new ExtraLarge(volume));
        if (imageURLContext.getImageURL() == null) {
            imageURLContext.setImageURLGenerator(new Large(volume));
        }
        if (imageURLContext.getImageURL() == null) {
            imageURLContext.setImageURLGenerator(new Medium(volume));
        }
        if (imageURLContext.getImageURL() == null) {
            imageURLContext.setImageURLGenerator(new Small(volume));
        }
        if (imageURLContext.getImageURL() == null) {
            imageURLContext.setImageURLGenerator(new NormalThumbnail(volume));
        }
        if (imageURLContext.getImageURL() == null) {
            imageURLContext.setImageURLGenerator(new SmallThumbnail(volume));
        }

        String title = volume.getVolumeInfo().getTitle() != null ? volume.getVolumeInfo().getTitle() : "Không có tiêu đề";
        String publisher = volume.getVolumeInfo().getPublisher() != null ? volume.getVolumeInfo().getPublisher() : "Không có nhà xuất bản";
        String publishedDate = volume.getVolumeInfo().getPublishedDate() != null ? volume.getVolumeInfo().getPublishedDate() : "Không có ngày xuất bản";
        String description = volume.getVolumeInfo().getDescription() != null ? volume.getVolumeInfo().getDescription() : "Không có mô tả";
        Integer pageCount = volume.getVolumeInfo().getPageCount() != null ? volume.getVolumeInfo().getPageCount() : 0;
        Double averageRating = volume.getVolumeInfo().getAverageRating() != null ? volume.getVolumeInfo().getAverageRating() : 0.0;
        String maturityRating = volume.getVolumeInfo().getMaturityRating() != null ? volume.getVolumeInfo().getMaturityRating() : "Không có đánh giá trưởng thành";
        String printType = volume.getVolumeInfo().getPrintType() != null ? volume.getVolumeInfo().getPrintType() : "Không có loại in";
        String language = volume.getVolumeInfo().getLanguage() != null ? volume.getVolumeInfo().getLanguage() : "Không có ngôn ngữ";
        String thumbnailUrl = imageURLContext.getImageURL() + "&fife=w800";
        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            thumbnailUrl = "https://via.placeholder.com/150";
        }
        System.out.println("Thumbnail URL: " + thumbnailUrl);
        Book newBook = new Book(
                title,
                str_authors,
                publisher,
                publishedDate,
                description,
                str_categories,
                pageCount,
                averageRating,
                maturityRating,
                printType,
                language,
                isbn_10,
                isbn_13,
                thumbnailUrl
        );
        return newBook;
    }
}
