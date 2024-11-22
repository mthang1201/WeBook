package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;

/**
 * The NormalThumbnail class is an implementation of the ImageURLGenerator interface.
 * It generates the thumbnail image URL for a given Volume object.
 */
public class NormalThumbnail implements ImageURLGenerator {

    private Volume volume;

    /**
     * Constructs a NormalThumbnail instance with the specified Volume.
     *
     * @param volume the Volume object for which the thumbnail URL will be generated
     */
    public NormalThumbnail(Volume volume) {
        this.volume = volume;
    }

    /**
     * Processes the volume's image URL and returns the thumbnail URL if available.
     *
     * @return the thumbnail URL if the image links are not null; otherwise, null
     */
    @Override
    public String processImageURL() {
        if (volume.getVolumeInfo().getImageLinks() != null) {
            return volume.getVolumeInfo().getImageLinks().getThumbnail();
        }
        return null;
    }
}
