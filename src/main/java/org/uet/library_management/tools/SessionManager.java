package org.uet.library_management.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.uet.library_management.core.entities.User;

/**
 * The SessionManager class is a utility class responsible for managing the current session state.
 * This includes user information and user-related properties such as avatar, name, and email.
 */
public class SessionManager {
    public static User user;

    public static final ObjectProperty<Image> currentAvatar = new SimpleObjectProperty<>();

    public static final ObjectProperty<String> currentName = new SimpleObjectProperty<>();

    public static final ObjectProperty<String> currentEmail = new SimpleObjectProperty<>();
}
