package org.uet.library_management.tools;

import javafx.scene.image.Image;

import javax.sql.rowset.serial.SerialException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for loading and managing images within the library management system.
 */
public class ImageLoaderUtil {
    private static final String PLACEHOLDER_PATH = "/org/uet/library_management/placeholder/165x249.png";
    private static final String FILLED_STAR_PATH = "/org/uet/library_management/icons/filled-star.png";
    private static final String EMPTY_STAR_PATH = "/org/uet/library_management/icons/empty-star.png";
    private static final String ARROW_PATH = "/org/uet/library_management/icons/arrow.png";
    private static final String PREFIX_URL = "/org/uet/library_management/icons/";
    private static final String INVALID_URL_1 = "null&fife=w800&format=webp";
    private static final String INVALID_URL_2 = "https://via.placeholder.com/150";

    /**
     * Resolves the image URL by checking for null or invalid URLs.
     * If the URL is invalid, returns the path to a placeholder image instead.
     *
     * @param imageUrl The URL of the image to be resolved. This can be null or an invalid URL.
     * @return The valid image URL or the placeholder image URL if the provided URL is invalid.
     */
    public static String resolveImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.equals(INVALID_URL_1) || imageUrl.equals(INVALID_URL_2)) {
            return ImageLoaderUtil.class.getResource(PLACEHOLDER_PATH).toExternalForm();
        }
        return imageUrl;
    }

    /**
     * Gets image.
     *
     * @param name the name
     * @return the image
     */
    public static Image getImage(String name) {
        return new Image(ImageLoaderUtil.class.getResourceAsStream(PREFIX_URL + name));
    }

    /**
     * Gets book mark image.
     *
     * @param isMarked the is marked
     * @return the book mark image
     */
    public static Image getBookMarkImage(boolean isMarked) {
        Image bookmarkImage = null;
        if (isMarked == false ) {
            bookmarkImage = ImageLoaderUtil.getImage("mark-white.png");
        } else {
            bookmarkImage = ImageLoaderUtil.getImage("mark-black.png");
        }
        return bookmarkImage;
    }

    /**
     * Gets arrow image.
     *
     * @return the arrow image
     */
    public static Image getArrowImage() {
        return new Image(ImageLoaderUtil.class.getResourceAsStream(ARROW_PATH));
    }

    /**
     * Generates a list of star images based on the provided rating.
     * The list will contain filled star images up to the rating value
     * and empty star images for the remaining positions up to a total of 5 stars.
     *
     * @param rating The rating value for which the star images are to be generated.
     *               It should be between 0 and 5, inclusive.
     * @return A list of star images representing the rating.
     */
    public static List<Image> getStarImages(int rating) {

        List<Image> starImages = new ArrayList<>();

        for (int i = 0; i < rating; i++) {
            starImages.add(new Image(ImageLoaderUtil.class.getResourceAsStream(FILLED_STAR_PATH)));
        }

        for (int i = rating; i < 5; i++) {
            starImages.add(new Image(ImageLoaderUtil.class.getResourceAsStream(EMPTY_STAR_PATH)));
        }

        return starImages;
    }
}
