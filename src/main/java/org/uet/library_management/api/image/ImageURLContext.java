package org.uet.library_management.api.image;

/**
 * A context class for handling image URL generation based on different strategies.
 * This class allows setting an implementation of the ImageURLGenerator interface
 * and provides a method to retrieve the processed image URL.
 */
public class ImageURLContext {
    private ImageURLGenerator imageURLGenerator;

    public void setImageURLGenerator(ImageURLGenerator imageURLGenerator) {
        this.imageURLGenerator = imageURLGenerator;
    }

    public String getImageURL() {
        return imageURLGenerator.processImageURL();
    }
}
