package org.uet.library_management.tools;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages the caching of image assets for the application. This class ensures that frequently
 * accessed images are loaded from the cache or file system to reduce redundant network requests.
 */
public class ImageCacheManager {
    private static final int MAX_CACHE_SIZE = 1000;

    private static Map<String, Image> imageCache;

    private static String cacheDir;

    private static ImageCacheManager instance;

    public static ImageCacheManager getInstance() {
        if (instance == null) {
            instance = new ImageCacheManager();
        }
        return instance;
    }

    /**
     * Constructs an ImageCacheManager and initializes the image cache.
     * Sets up a directory for storing cached image files.
     * The cache uses a Least Recently Used (LRU) policy for removing the oldest entries when the cache exceeds its maximum size.
     */
    public ImageCacheManager() {
        imageCache = new LinkedHashMap<String, Image>(MAX_CACHE_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Image> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };

        cacheDir = "cache/";
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Loads an image based on the provided ISBN and title.
     * The image is retrieved from the cache if available;
     * otherwise, it is loaded from the given URL, cached, and then returned.
     *
     * @param isbn13    The ISBN-13 identifier of the book.
     * @param title     The title of the book.
     * @param imageLinks The URL of the image to load.
     * @return The loaded image.
     */
    public Image loadImage(String isbn13, String title, String imageLinks) {
        String cacheKey = isbn13 + "_" + title;

        imageLinks = ImageLoaderUtil.resolveImageUrl(imageLinks);

        File cacheFile = new File(cacheDir + cacheKey + ".jpeg");

        if (cacheFile.exists()) {
            return new Image(cacheFile.toURI().toString(), true);
        } else if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        } else {
            Image image = new Image(imageLinks, true);
          
            imageCache.put(cacheKey, image);
          
            return image;
        }
    }

    /**
     * Saves the given image to the cache directory using the specified cache key.
     * The image is saved in JPEG format with the cache key used as the file name.
     *
     * @param cacheKey The key used to identify and store the image in the cache.
     *                 This key is transformed to replace any '|' characters with '_'.
     * @param image    The image to be saved to the cache.
     */
    private void saveImageToCache(String cacheKey, Image image) {
        File newFile = new File(cacheDir + cacheKey.replaceAll("[|]", "_") + ".jpeg");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpeg", newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
