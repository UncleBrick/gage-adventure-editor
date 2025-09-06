package com.phantomcoder.adventureeditor.constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConstants {
    /** The absolute path to the application's base (working) directory. */
    public static final Path BASE_DIR = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

    private AppConstants() { /* Prevent instantiation */ }
}