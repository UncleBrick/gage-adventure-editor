package com.phantomcoder.adventureeditor.constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Import the shared flag groups
import static com.phantomcoder.adventureeditor.constants.SharedFlagConstants.NARRATIVE_FLAG_GROUP;
import static com.phantomcoder.adventureeditor.constants.SharedFlagConstants.TIME_FLAG_GROUP;


/**
 * Defines constant values for ambiance event flags.
 */
public final class AmbianceFlagConstants {

    // --- Ambiance-Specific Narrative Flags ---
    public static final String HINT_GIVER = "HINT_GIVER";
    public static final String MISLEADING = "MISLEADING";
    public static final String QUEST_PROGRESS = "QUEST_PROGRESS";

    // --- Ambiance-Specific Mechanical Flags ---
    public static final String TRIGGER_ONCE = "TRIGGER_ONCE";

    /** A map of all flag groups, used by the UI to dynamically generate checkboxes. */
    public static final Map<String, List<String>> CATEGORIZED_FLAGS = new LinkedHashMap<>();

    static {
        // --- Ambiance-Specific Categories ---
        CATEGORIZED_FLAGS.put("Narrative", List.of(
                HINT_GIVER, MISLEADING, QUEST_PROGRESS
        ));

        CATEGORIZED_FLAGS.put("Mechanical", List.of(
                TRIGGER_ONCE
        ));

        // --- Shared Categories ---
        // Add all shared flag groups for use in the Ambiance UI
        CATEGORIZED_FLAGS.putAll(NARRATIVE_FLAG_GROUP);
        CATEGORIZED_FLAGS.putAll(TIME_FLAG_GROUP);
    }

    private AmbianceFlagConstants() { /* Prevent instantiation */ }
}