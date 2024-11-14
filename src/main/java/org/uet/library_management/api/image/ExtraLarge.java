package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;

/**
 * The ExtraLarge class implements the ImageURLGenerator interface to generate
 * the URL for an extra-large image from the provided Volume object.
 *
 * The class is responsible for extracting the extra-large image URL from
 * the Volume object's image links, which are encapsulated within its volume info.
 * If no image links are available, the processImageURL method returns null.
 */
public class ExtraLarge implements ImageURLGenerator {
    private Volume volume;

    public ExtraLarge(Volume volume) {
        this.volume = volume;
    }

    /**
     * Extracts the extra-large image URL from the Volume object.
     * Checks if image links are available within the volume info
     * and returns the extra-large image URL if present; otherwise, returns null.
     *
     * @return the extra-large image URL as a String, or null if no extra-large image URL is available
     */
    @Override
    public String processImageURL() {
        if (volume.getVolumeInfo().getImageLinks() != null) {
            return volume.getVolumeInfo().getImageLinks().getExtraLarge();
        }
        return null;
    }
}
