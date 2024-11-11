package org.uet.library_management.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.uet.library_management.core.entities.User;

public class SessionManager {
    public static User user;

    public static final ObjectProperty<Image> currentAvatar = new SimpleObjectProperty<>();

    public static final ObjectProperty<String> currentName = new SimpleObjectProperty<>();

    public static final ObjectProperty<String> currentEmail = new SimpleObjectProperty<>();
}
