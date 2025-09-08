package com.phantomcoder.adventureeditor.util;

import com.phantomcoder.adventureeditor.constants.DataConstants;
import java.io.File;
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
     * in the source path, keeping the filename identical.
     *
     * @param sourcePath       The full path to the source file (e.g., .../rooms/00_00_00.json).
     * @param sourceDataTypeKey The key for the source data type in DataConstants (e.g., "ROOMS").
     * @param targetDataTypeKey The key for the target data type in DataConstants (e.g., "OBJECTS").
     * @return The calculated absolute path for the parallel file.
     * @throws IllegalArgumentException if the data type keys are invalid or the path is malformed.
     */
    public static Path getParallelPath(Path sourcePath, String sourceDataTypeKey, String targetDataTypeKey) {
        // 1. Look up directory names from constants
        String sourceDir = DataConstants.DATA_DIRECTORIES.get(sourceDataTypeKey);
        String targetDir = DataConstants.DATA_DIRECTORIES.get(targetDataTypeKey);

        if (sourceDir == null || targetDir == null) {
            throw new IllegalArgumentException("Invalid source or target data type key provided.");
        }

        // 2. Prepare directory segments for replacement
        String sourcePathStr = sourcePath.toString();
        String sourceDirSegment = File.separator + sourceDir + File.separator;
        String targetDirSegment = File.separator + targetDir + File.separator;

        if (!sourcePathStr.contains(sourceDirSegment)) {
            throw new IllegalArgumentException("Source path does not contain the expected directory segment: " + sourceDirSegment);
        }

        // 3. Replace the directory segment and return the new Path
        String targetPathStr = sourcePathStr.replace(sourceDirSegment, targetDirSegment);
        System.out.println("-------------------------[getParallelPath]------------------------");
        System.out.println(targetPathStr);
        System.out.println("------------------------------------------------------------------");
        return Paths.get(targetPathStr);
    }
}