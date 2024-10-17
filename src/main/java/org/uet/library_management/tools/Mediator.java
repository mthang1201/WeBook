package org.uet.library_management.tools;

public class Mediator {
    private static String text;

    private static Mediator instance;

    private Mediator() {}

    public static Mediator getInstance() {
        if (instance == null) {
            instance = new Mediator();
        }
        return instance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        Mediator.text = text;
    }
}
