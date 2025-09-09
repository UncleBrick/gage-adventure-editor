package com.phantomcoder.adventureeditor.constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConstants {
    /** The absolute path to the application's base (working) directory. */
    public static final Path BASE_DIR = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

    /** The required character length for an auto-generated ambiance content hash. */
    public static final int AMBIANCE_HASH_LENGTH = 5;

    /** The property key for the 'show welcome dialog' preference. */
    public static final String PREF_SHOW_WELCOME_DIALOG = "ui.dialogs.show_welcome_screen";

    private AppConstants() { /* Prevent instantiation */ }
}