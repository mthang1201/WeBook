package org.uet.library_management.tools;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;

import javax.imageio.ImageIO;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public ImageCacheManager() {
        imageCache = new LinkedHashMap<String, Image>(MAX_CACHE_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Image> eldest) {
                //System.out.println("Remove" + eldest.getKey());
                return size() > MAX_CACHE_SIZE;
            }
        };

        cacheDir = "cache/";
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public Image loadImage(String isbn13, String title, String imageLinks) {
        String cacheKey = isbn13 + "_" + title;

        if (imageLinks == null || imageLinks.equals("null&fife=w800&format=webp") || imageLinks.equals("https://via.placeholder.com/150")) {
            imageLinks = getClass().getResource("/org/uet/library_management/placeholder/165x249.png").toExternalForm();
            return new Image(imageLinks, true);
        }

        File cacheFile = new File(cacheDir + cacheKey + ".jpeg");

        if (cacheFile.exists()) {
            return new Image(cacheFile.toURI().toString(), true);
        } else if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        } else {
            Image image = new Image(imageLinks, true);
            imageCache.put(cacheKey, image);
            //saveImageToCache(cacheKey, image);
            return image;
        }
    }

    private void saveImageToCache(String cacheKey, Image image) {
        File newFile = new File(cacheDir + cacheKey.replaceAll("[|]", "_") + ".jpeg");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpeg", newFile);
            System.out.println("Image saved at: " + newFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
