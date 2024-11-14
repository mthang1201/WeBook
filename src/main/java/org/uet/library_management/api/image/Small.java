package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;

/**
 * The Small class implements the ImageURLGenerator interface to generate
 * small image URLs for a given volume. It retrieves the small image URL
 * from the volume's volume information.
 */
public class Small implements ImageURLGenerator {
    private Volume volume;

    public Small(Volume volume) {
        this.volume = volume;
    }

    /**
     * Processes and retrieves the small image URL from the associated
     * volume's volume information.
     *
     * @return The small image URL if available, otherwise null.
     */
    @Override
    public String processImageURL() {
        if (volume.getVolumeInfo().getImageLinks() != null) {
            return volume.getVolumeInfo().getImageLinks().getSmall();
        }
        return null;
    }
}
