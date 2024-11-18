package org.uet.library_management.api.image;

import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volume.VolumeInfo;
import com.google.api.services.books.v1.model.Volume.VolumeInfo.ImageLinks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the SmallThumbnail class.
 */
public class SmallThumbnailTest {

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
    public void testProcessImageURL_withValidSmallThumbnail() {
        // Set up the ImageLinks with a valid small thumbnail URL
        imageLinks.setSmallThumbnail("http://example.com/small-thumbnail.jpg");
        volumeInfo.setImageLinks(imageLinks);

        SmallThumbnail smallThumbnail = new SmallThumbnail(volume);

        String result = smallThumbnail.processImageURL();
        assertEquals("http://example.com/small-thumbnail.jpg", result, "The URL should match the expected small thumbnail value.");
    }

    @Test
    public void testProcessImageURL_withNullImageLinks() {
        // Leave imageLinks null
        volumeInfo.setImageLinks(null);

        SmallThumbnail smallThumbnail = new SmallThumbnail(volume);

        String result = smallThumbnail.processImageURL();
        assertNull(result, "The result should be null when image links are not set.");
    }

    @Test
    public void testProcessImageURL_withEmptySmallThumbnail() {
        // Set up ImageLinks with an empty string as the small thumbnail URL
        imageLinks.setSmallThumbnail("");
        volumeInfo.setImageLinks(imageLinks);

        SmallThumbnail smallThumbnail = new SmallThumbnail(volume);

        String result = smallThumbnail.processImageURL();
        assertEquals("", result, "The result should be an empty string when the small thumbnail URL is empty.");
    }
}
