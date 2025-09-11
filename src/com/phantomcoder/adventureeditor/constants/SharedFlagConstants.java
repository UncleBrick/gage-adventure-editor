package com.phantomcoder.adventureeditor.constants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains shared constants that are used by multiple systems, such as Rooms, Objects, and Ambiance.
 * This class follows the established project pattern for defining and grouping constants.
 */
public final class SharedFlagConstants {

    // --- Time of Day Flags ---
    public static final String EARLY_MORNING = "EARLY_MORNING";
    public static final String MORNING = "MORNING";
    public static final String NOON = "NOON";
    public static final String AFTERNOON = "AFTERNOON";
    public static final String EVENING = "EVENING";
    public static final String NIGHT = "NIGHT";

    // --- Shared Narrative Flags ---
    public static final String STARTS_QUEST = "STARTS_QUEST";
    public static final String QUEST_ITEM = "QUEST_ITEM";
    public static final String KEY_ITEM = "KEY_ITEM";
    public static final String LORE_ITEM = "LORE_ITEM";


    /** A map containing the time flag group, used by the UI to generate checkboxes. */
    public static final Map<String, List<String>> TIME_FLAG_GROUP = new LinkedHashMap<>();

    /** A map containing shared narrative flags for UI generation. */
    public static final Map<String, List<String>> NARRATIVE_FLAG_GROUP = new LinkedHashMap<>();


    static {
        TIME_FLAG_GROUP.put("Time Of Day", Arrays.asList(
                EARLY_MORNING, MORNING, NOON, AFTERNOON, EVENING, NIGHT
        ));

        NARRATIVE_FLAG_GROUP.put("Narrative", Arrays.asList(
                STARTS_QUEST, QUEST_ITEM, KEY_ITEM, LORE_ITEM
        ));
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private SharedFlagConstants() {}
}