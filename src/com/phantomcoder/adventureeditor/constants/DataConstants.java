package com.phantomcoder.adventureeditor.constants;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Defines constant values for data paths and directory names used throughout the application.
 * This centralization prevents the use of "magic strings" and provides a scalable way
 * to manage data directories.
 */
public final class DataConstants {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DataConstants() {}

    // --- Base Path Constants ---
    public static final String RESOURCES_DIRECTORY_NAME = "resources";
    public static final String DATA_DIRECTORY_NAME = "data";
    public static final String BASE_DATA_PATH = RESOURCES_DIRECTORY_NAME + "/" + DATA_DIRECTORY_NAME;


    // --- Individual Directory Name Constants ---
    public static final String ROOMS_DIRECTORY_NAME = "rooms";
    public static final String OBJECTS_DIRECTORY_NAME = "objects";
    public static final String AMBIANCE_DIRECTORY_NAME = "ambiance";
    public static final String NPCS_DIRECTORY_NAME = "npcs";

    /**
     * A map that associates data type keys with their corresponding directory name constants.
     * This allows for dynamic handling of game data directories, making it easy to add
     * new data types in the future without changing the core persistence logic.
     * A LinkedHashMap is used to preserve insertion order.
     */
    public static final Map<String, String> DATA_DIRECTORIES;

    static {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ROOMS", ROOMS_DIRECTORY_NAME);
        map.put("OBJECTS", OBJECTS_DIRECTORY_NAME);
        map.put("AMBIANCE", AMBIANCE_DIRECTORY_NAME);
        map.put("NPCS", NPCS_DIRECTORY_NAME);
        // Add new data types here in the future

        DATA_DIRECTORIES = Collections.unmodifiableMap(map);
    }
}