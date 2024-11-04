package org.uet.library_management.tools;

import javafx.scene.image.Image;

public class ImageLoaderUtil {
    private static final String PLACEHOLDER_PATH = "/org/uet/library_management/placeholder/165x249.png";
    private static final String INVALID_URL_1 = "null&fife=w800&format=webp";
    private static final String INVALID_URL_2 = "https://via.placeholder.com/150";

    public static String resolveImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.equals(INVALID_URL_1) || imageUrl.equals(INVALID_URL_2)) {
            return ImageLoaderUtil.class.getResource(PLACEHOLDER_PATH).toExternalForm();
        }
        return imageUrl;
    }
}
