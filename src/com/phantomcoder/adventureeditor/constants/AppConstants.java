package com.phantomcoder.adventureeditor.constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConstants {
    /** The absolute path to the application's base (working) directory. */
    public static final Path BASE_DIR = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

    /** The property key for the 'show welcome dialog' preference. */
    public static final String PREF_SHOW_WELCOME_DIALOG = "ui.dialogs.show_welcome_screen";

    // --- NEW: Constants for the ID Slug Generator ---

    /** The minimum number of words a description must have to generate a slug. */
    public static final int MIN_DESC_WORDS_FOR_SLUG = 5;

    /** The number of significant (non-stopword) words to use when creating a slug. */
    public static final int SLUG_WORD_COUNT = 4;


    private AppConstants() { /* Prevent instantiation */ }
}
