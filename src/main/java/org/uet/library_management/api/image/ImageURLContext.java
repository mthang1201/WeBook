package org.uet.library_management.api.image;

public class ImageURLContext {
    private ImageURLGenerator imageURLGenerator;

    public void setImageURLGenerator(ImageURLGenerator imageURLGenerator) {
        this.imageURLGenerator = imageURLGenerator;
    }

    public String getImageURL() {
        return imageURLGenerator.processImageURL();
    }
}
