package com.phantomcoder.adventureeditor.util;

import com.phantomcoder.adventureeditor.constants.DataConstants;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathUtil {

    private PathUtil() { /* Prevent instantiation */ }

    /**
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

    /**
     * Calculates the path for a parallel data file by replacing the data type directory
     * in the source path, keeping the filename identical. This is now robust enough
     * to handle nested sub-area directories.
     *
     * @param sourcePath       The full path to the source file (e.g., .../rooms/00_00_00.json).
     * @param sourceDataTypeKey The key for the source data type in DataConstants (e.g., "ROOMS").
     * @param targetDataTypeKey The key for the target data type in DataConstants (e.g., "OBJECTS").
     * @return The calculated absolute path for the parallel file.
     * @throws IllegalArgumentException if the data type keys are invalid or the path is malformed.
     */
    public static Path getParallelPath(Path sourcePath, String sourceDataTypeKey, String targetDataTypeKey) {
        // 1. Look up directory names from constants
        String sourceDirName = DataConstants.DATA_DIRECTORIES.get(sourceDataTypeKey);
        String targetDirName = DataConstants.DATA_DIRECTORIES.get(targetDataTypeKey);

        if (sourceDirName == null || targetDirName == null) {
            throw new IllegalArgumentException("Invalid source or target data type key provided.");
        }

        // 2. Get the parent directory of the source file. This is the data type directory.
        Path sourceDataTypeDir = sourcePath.getParent();
        if (sourceDataTypeDir == null || !sourceDataTypeDir.getFileName().toString().equals(sourceDirName)) {
            throw new IllegalArgumentException("Source path does not appear to be in a valid '" + sourceDirName + "' directory.");
        }

        // 3. Get the parent of that directory. This is the common root path
        //    (e.g., data/location/area/ or data/location/area/sub_area/)
        Path commonRoot = sourceDataTypeDir.getParent();
        if (commonRoot == null) {
            throw new IllegalArgumentException("Could not determine a common root path from the source.");
        }

        // 4. Get the original filename.
        Path fileName = sourcePath.getFileName();
        if (fileName == null) {
            throw new IllegalArgumentException("Could not extract a filename from the source path.");
        }

        // 5. Construct the new path from the common root, the target directory, and the filename.
        return commonRoot.resolve(targetDirName).resolve(fileName);
    }
}
