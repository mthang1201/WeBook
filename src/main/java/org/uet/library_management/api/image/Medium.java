package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;

public class Medium implements ImageURLGenerator {
    private Volume volume;

    public Medium(Volume volume) {
        this.volume = volume;
    }

    @Override
    public String processImageURL() {
        if (volume.getVolumeInfo().getImageLinks() != null) {
            return volume.getVolumeInfo().getImageLinks().getMedium();
        }
        return null;
    }
}
