package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;

/**
 * The Large class is responsible for generating a large image URL from a provided
 * Volume object. It implements the ImageURLGenerator interface and provides
 * the logic for extracting the large image URL from the Volume's image links.
 */
public class Large implements ImageURLGenerator {
    private Volume volume;

    public Large(Volume volume) {
        this.volume = volume;
    }

    /**
     * Extracts the large image URL from the provided Volume object's image links.
     *
     * @return the large image URL if available; otherwise, returns null.
     */
    @Override
    public String processImageURL() {
        if (volume.getVolumeInfo().getImageLinks() != null) {
            return volume.getVolumeInfo().getImageLinks().getLarge();
        }
        return null;
    }
}
