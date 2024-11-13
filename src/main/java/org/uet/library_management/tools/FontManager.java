package org.uet.library_management.tools;

import javafx.scene.text.Font;
import javafx.scene.control.*;
/**
 * The FontManager class provides methods to manage and load custom fonts
 * for use in JavaFX applications. It includes functionalities to load a font
 * from a specified path and apply it to a JavaFX Label.
 */
public class FontManager {
    private static final String PREFIX_URL = "/org/uet/library_management/fonts/";

    /**
     * Loads a custom font from the specified file path and sets its size.
     * If the font cannot be loaded, it defaults to Arial.
     *
     * @param myFont The path to the font file.
     * @param size The size of the font to load.
     * @return The loaded font object, or Arial if the custom font cannot be loaded.
     */
    public static Font loadFont(String myFont, double size) {
        Font font = Font.loadFont(FontManager.class.getResourceAsStream(PREFIX_URL + myFont), size);
        if (font == null) {
            font = Font.font("Arial", size);
        }
        return font;
    }

    /**
     * Applies a custom font to a specified Label.
     *
     * @param label The Label to which the font will be applied.
     * @param myFont The path to the custom font file.
     * @param size The size of the font to apply.
     */
    public static void applyFontToLabel(Label label, String myFont, double size) {
        label.setFont(loadFont(myFont, size));
    }
}
