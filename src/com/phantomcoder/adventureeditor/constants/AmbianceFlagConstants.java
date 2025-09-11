package com.phantomcoder.adventureeditor.constants;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines constant values for ambiance event flags.
 */
public final class AmbianceFlagConstants {

    // --- Narrative Flags ---
    public static final String QUEST_STARTER = "QUEST_STARTER";
    public static final String HINT_GIVER = "HINT_GIVER";
    public static final String MISLEADING = "MISLEADING";
    public static final String QUEST_PROGRESS = "QUEST_PROGRESS";

    // --- Mechanical Flags ---
    public static final String TRIGGER_ONCE = "TRIGGER_ONCE";

    // --- Time-Based Flags ---
    public static final String REQUIRES_TIME_PHASE = "REQUIRES_TIME_PHASE";

    /** A map of all flag groups, used by the UI to dynamically generate checkboxes. */
    public static final Map<String, List<String>> CATEGORIZED_FLAGS;

    static {
        Map<String, List<String>> map = new LinkedHashMap<>();

        map.put("Narrative", List.of(
                QUEST_STARTER, HINT_GIVER, MISLEADING, QUEST_PROGRESS
        ));

        map.put("Mechanical", List.of(
                TRIGGER_ONCE
        ));

        map.put("Time-Based", List.of(
                REQUIRES_TIME_PHASE
        ));

        CATEGORIZED_FLAGS = Collections.unmodifiableMap(map);
    }

    private AmbianceFlagConstants() { /* Prevent instantiation */ }
}