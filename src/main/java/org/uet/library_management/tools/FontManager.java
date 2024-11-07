package org.uet.library_management.tools;

import javafx.scene.text.Font;
import javafx.scene.control.*;
public class FontManager {
    private static final String PREFIX_URL = "/org/uet/library_management/fonts/";

    public static Font loadFont(String myFont, double size) {
        Font font = Font.loadFont(FontManager.class.getResourceAsStream(PREFIX_URL + myFont), size);
        if (font == null) {
            font = Font.font("Arial", size);
        }
        return font;
    }

    public static void applyFontToLabel(Label label, String myFont, double size) {
        label.setFont(loadFont(myFont, size));
    }
}
