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

public class ImageLoaderUtil {
    private static final String PLACEHOLDER_PATH = "/org/uet/library_management/placeholder/165x249.png";
    private static final String FILLED_STAR_PATH = "/org/uet/library_management/icons/filled-star.png";
    private static final String EMPTY_STAR_PATH = "/org/uet/library_management/icons/empty-star.png";
    private static final String INVALID_URL_1 = "null&fife=w800&format=webp";
    private static final String INVALID_URL_2 = "https://via.placeholder.com/150";

    public static String resolveImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.equals(INVALID_URL_1) || imageUrl.equals(INVALID_URL_2)) {
            return ImageLoaderUtil.class.getResource(PLACEHOLDER_PATH).toExternalForm();
        }
        return imageUrl;
    }

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
