package com.phantomcoder.adventureeditor.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathUtil {

    private PathUtil() { /* Prevent instantiation */ }

    /**
     * FIX: Renamed from getProjectRootPath to match the original dependency.
     * Gets the root directory of the project (the application's base directory).
     * @return The absolute path to the project's root.
     */
    public static Path getAppBaseDirectory() {
        return Paths.get("").toAbsolutePath();
    }

    /**
     * Sanitizes a string to make it safe for use as a file or directory name.
     * @param input The string to sanitize.
     * @return A file-system-safe version of the string.
     */
    public static String toSafeFileName(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        return input
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-z0-9_]", "");
    }
}