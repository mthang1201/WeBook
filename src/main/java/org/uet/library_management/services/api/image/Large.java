package org.uet.library_management.services.api.image;

import com.google.api.services.books.v1.model.Volume;

public class Large implements ImageURLGenerator {
    private Volume volume;

    public Large(Volume volume) {
        this.volume = volume;
    }

    @Override
    public String processImageURL() {
        if (volume.getVolumeInfo().getImageLinks() != null) {
            return volume.getVolumeInfo().getImageLinks().getLarge();
        }
        return null;
    }
}
