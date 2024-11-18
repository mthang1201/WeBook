package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volume.VolumeInfo;
import com.google.api.services.books.v1.model.Volume.VolumeInfo.ImageLinks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the NormalThumbnail class.
 */
public class NormalThumbnailTest {

    private Volume volume;
    private VolumeInfo volumeInfo;
    private ImageLinks imageLinks;

    @BeforeEach
    public void setUp() {
        volume = new Volume();
        volumeInfo = new VolumeInfo();
        imageLinks = new ImageLinks();
        volume.setVolumeInfo(volumeInfo);
    }

    @Test
    public void testProcessImageURL_withValidThumbnail() {
        // Set up the ImageLinks with a valid thumbnail URL
        imageLinks.setThumbnail("http://example.com/thumbnail.jpg");
        volumeInfo.setImageLinks(imageLinks);

        NormalThumbnail normalThumbnail = new NormalThumbnail(volume);

        String result = normalThumbnail.processImageURL();
        assertEquals("http://example.com/thumbnail.jpg", result, "The URL should match the expected value.");
    }

    @Test
    public void testProcessImageURL_withNullImageLinks() {
        // Leave imageLinks null
        volumeInfo.setImageLinks(null);

        NormalThumbnail normalThumbnail = new NormalThumbnail(volume);

        String result = normalThumbnail.processImageURL();
        assertNull(result, "The result should be null when image links are not set.");
    }

    @Test
    public void testProcessImageURL_withEmptyThumbnail() {
        // Set up ImageLinks with an empty string as the thumbnail URL
        imageLinks.setThumbnail("");
        volumeInfo.setImageLinks(imageLinks);

        NormalThumbnail normalThumbnail = new NormalThumbnail(volume);

        String result = normalThumbnail.processImageURL();
        assertEquals("", result, "The result should be an empty string when the thumbnail URL is empty.");
    }
}
