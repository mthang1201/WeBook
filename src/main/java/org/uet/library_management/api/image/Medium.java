package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;

/**
 * The Medium class implements the ImageURLGenerator interface to
 * provide a specific implementation for generating medium-sized
 * image URLs from a Volume object.
 */
public class Medium implements ImageURLGenerator {
    private Volume volume;

    public Medium(Volume volume) {
        this.volume = volume;
    }

    /**
     * Processes the image URL by retrieving the medium-sized image link
     * from the Volume object associated with this instance. If no image links
     * are available, it returns null.
     *
     * @return the medium-sized image URL as a String, or null if no image links are available
     */
    @Override
    public String processImageURL() {
        if (volume.getVolumeInfo().getImageLinks() != null) {
            return volume.getVolumeInfo().getImageLinks().getMedium();
        }
        return null;
    }
}
