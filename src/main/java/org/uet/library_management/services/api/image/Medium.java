package org.uet.library_management.services.api.image;

import com.google.api.services.books.v1.model.Volume;

public class Medium implements ImageURLGenerator {
    private Volume volume;

    public Medium(Volume volume) {
        this.volume = volume;
    }

    @Override
    public String processImageURL() {
        return volume.getVolumeInfo().getImageLinks().getMedium();
    }
}
