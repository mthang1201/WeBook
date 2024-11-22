package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;

/**
 * The SmallThumbnail class is an implementation of the ImageURLGenerator interface.
 * It generates the small thumbnail image URL for a given Volume object.
 */
public class SmallThumbnail implements ImageURLGenerator {

    private Volume volume;

    /**
     * Constructs a SmallThumbnail instance with the specified Volume.
     *
     * @param volume the Volume object for which the small thumbnail URL will be generated
     */
    public SmallThumbnail(Volume volume) {
        this.volume = volume;
    }

    /**
     * Processes the image URL for the small thumbnail associated with the volume.
     * Checks if the volume contains image links and returns the small thumbnail URL if available.
     *
     * @return the URL of the small thumbnail image if available; otherwise, null
     */
    @Override
    public String processImageURL() {
        if (volume.getVolumeInfo().getImageLinks() != null) {
            return volume.getVolumeInfo().getImageLinks().getSmallThumbnail();
        }
        return null;
    }
}
