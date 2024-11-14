package org.uet.library_management.api.image;

/**
 * The ImageURLGenerator interface defines a contract for classes that
 * are responsible for generating image URLs. Implementations of this
 * interface are expected to provide the logic for extracting specific
 * types of image URLs from a source (e.g., a Volume object).
 */
public interface ImageURLGenerator {
   String processImageURL();
}
