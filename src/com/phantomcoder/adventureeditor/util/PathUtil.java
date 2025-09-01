package com.phantomcoder.adventureeditor.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for manipulating file paths and strings for OS compatibility.
 */
public class PathUtil {

    private PathUtil() {
        // Private constructor to prevent instantiation of this utility class
    }

    /**
     * Gets the base directory of the application. This typically points to the
     * directory where the application was launched from or the project root.
     *
     * @return The absolute path to the application's base directory.
     */
    public static Path getAppBaseDirectory() {
        return Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    }

    /**
     * Converts a given string into an OS-safe, all-lowercase format suitable for file paths,
     * preserving existing underscores. It strips leading/trailing spaces, replaces
     * inner spaces with underscores, and removes other non-alphanumeric characters
     * EXCEPT for underscores.
     * Example: "My Test_Room 0_0" -> "my_test_room_0_0"
     *          "Area One (Level_2)" -> "area_one_level_2"
     *
     * @param input The string to convert.
     * @return The lowercase string with spaces replaced by underscores, preserving existing underscores.
     */
    public static String toOsSafeLowerCase(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        String sanitized = input.trim()
                                // Allow letters, digits, spaces, AND underscores
                                .replaceAll("[^a-zA-Z0-9 _]", "") // Modified: Preserve underscore
                                .replaceAll("\\s+", "_")        // Replace one or more spaces with a single underscore
                                .toLowerCase();                   // Convert the entire string to lowercase

        return sanitized;
    }
}
