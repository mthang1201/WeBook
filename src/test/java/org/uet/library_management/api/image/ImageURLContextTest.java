package org.uet.library_management.api.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the ImageURLContext class.
 */
public class ImageURLContextTest {

    private ImageURLContext context;

    @BeforeEach
    public void setUp() {
        context = new ImageURLContext();
    }

    @Test
    public void testGetImageURL_withValidGenerator() {
        // Create a mock implementation of ImageURLGenerator
        ImageURLGenerator mockGenerator = new ImageURLGenerator() {
            @Override
            public String processImageURL() {
                return "http://example.com/sample-image.jpg";
            }
        };

        // Set the mock generator in the context
        context.setImageURLGenerator(mockGenerator);

        // Verify the output
        String result = context.getImageURL();
        assertEquals("http://example.com/sample-image.jpg", result, "The URL should match the expected value.");
    }

    @Test
    public void testGetImageURL_withNullGenerator() {
        // Do not set a generator to simulate a null scenario
        Exception exception = assertThrows(NullPointerException.class, () -> {
            context.getImageURL();
        });

        // Verify the exception message if needed (optional)
        assertNotNull(exception.getMessage());
    }
}