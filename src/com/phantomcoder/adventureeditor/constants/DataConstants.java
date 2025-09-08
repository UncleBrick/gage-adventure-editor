package com.phantomcoder.adventureeditor.constants;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DataConstants {

    private DataConstants() {}

    // --- Wrapper Metadata ---
    public static final String FILE_DATA_WRAPPER_VERSION = "1.0";

    // --- Base Path Constants ---
    public static final String RESOURCES_DIRECTORY_NAME = "resources";
    public static final String DATA_DIRECTORY_NAME = "data";
    public static final String BASE_DATA_PATH = RESOURCES_DIRECTORY_NAME + "/" + DATA_DIRECTORY_NAME;

    // --- Individual Directory Name Constants ---
    public static final String ROOMS_DIRECTORY_NAME = "rooms";
    public static final String AMBIANCES_DIRECTORY_NAME = "ambiances";
    public static final String OBJECTS_DIRECTORY_NAME = "objects";
    public static final String NPCS_DIRECTORY_NAME = "npcs";
    public static final String COMMANDS_DIRECTORY_NAME = "commands";
    public static final String EXITS_DIRECTORY_NAME = "exits";
    public static final String MAGIC_DIRECTORY_NAME = "magic";
    public static final String QUESTS_DIRECTORY_NAME = "quests";

    public static final Map<String, String> DATA_DIRECTORIES;
    static {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ROOMS", ROOMS_DIRECTORY_NAME);
        map.put("AMBIANCES", AMBIANCES_DIRECTORY_NAME);
        map.put("OBJECTS", OBJECTS_DIRECTORY_NAME);
        map.put("NPCS", NPCS_DIRECTORY_NAME);
        map.put("COMMANDS", COMMANDS_DIRECTORY_NAME);
        map.put("EXITS", EXITS_DIRECTORY_NAME);
        map.put("MAGIC", MAGIC_DIRECTORY_NAME);
        map.put("QUESTS", QUESTS_DIRECTORY_NAME);
        DATA_DIRECTORIES = Collections.unmodifiableMap(map);
    }

    public static final Map<String, String> DATA_FILE_TYPES;
    static {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ROOMS", "Room Data");
        map.put("AMBIANCES", "Ambiance Data");
        map.put("OBJECTS", "Object Data");
        map.put("NPCS", "NPC Data");
        map.put("COMMANDS", "Command Data");
        map.put("EXITS", "Exit Data");
        map.put("MAGIC", "Magic Data");
        map.put("QUESTS", "Quest Data");
        DATA_FILE_TYPES = Collections.unmodifiableMap(map);
    }
}