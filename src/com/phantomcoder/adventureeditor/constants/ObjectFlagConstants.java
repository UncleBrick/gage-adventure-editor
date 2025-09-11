package com.phantomcoder.adventureeditor.constants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Import the shared constants to be used in the flag groups
import static com.phantomcoder.adventureeditor.constants.SharedFlagConstants.KEY_ITEM;
import static com.phantomcoder.adventureeditor.constants.SharedFlagConstants.LORE_ITEM;
import static com.phantomcoder.adventureeditor.constants.SharedFlagConstants.QUEST_ITEM;
import static com.phantomcoder.adventureeditor.constants.SharedFlagConstants.STARTS_QUEST;
import static com.phantomcoder.adventureeditor.constants.SharedFlagConstants.TIME_FLAG_GROUP;


public final class ObjectFlagConstants {

    // --- Binding & Ownership ---
    public static final String BINDS_ON_EQUIP = "BINDS_ON_EQUIP";
    public static final String BINDS_ON_PICKUP = "BINDS_ON_PICKUP";
    public static final String BINDS_ON_USE = "BINDS_ON_USE";
    public static final String CANNOT_BE_BANKED = "CANNOT_BE_BANKED";
    public static final String UNIQUE = "UNIQUE";
    public static final String UNIQUE_EQUIPPED = "UNIQUE_EQUIPPED";

    // --- Durability & Destruction ---
    public static final String CANNOT_BE_REPAIRED = "CANNOT_BE_REPAIRED";
    public static final String CANNOT_BE_SALVAGED = "CANNOT_BE_SALVAGED";
    public static final String CONSUMED_ON_USE = "CONSUMED_ON_USE";
    public static final String DESTROY_ON_DEATH = "DESTROY_ON_DEATH";
    public static final String HAS_DURABILITY = "HAS_DURABILITY";
    public static final String INDESTRUCTIBLE = "INDESTRUCTIBLE";
    public static final String TEMPORARY_EXPIRES = "TEMPORARY_EXPIRES";

    // --- Quest, Story, & Cursed (Object Specific) ---
    public static final String ATTRACTS_AGGRESSION = "ATTRACTS_AGGRESSION";
    public static final String CURSED = "CURSED";
    public static final String DRAINS_LIFE_MANA = "DRAINS_LIFE_MANA";
    public static final String FRAGILE = "FRAGILE";
    public static final String IS_HIDDEN = "IS_HIDDEN";

    // --- Special & Miscellaneous ---
    public static final String CLASS_SPECIFIC = "CLASS_SPECIFIC";
    public static final String FACTION_SPECIFIC = "FACTION_SPECIFIC";
    public static final String RACE_SPECIFIC = "RACE_SPECIFIC";
    public static final String REQUIRES_ATTUNEMENT = "REQUIRES_ATTUNEMENT";
    public static final String STACKABLE = "STACKABLE";
    public static final String UNMODIFIABLE = "UNMODIFIABLE";

    // --- Trade & Economy ---
    public static final String CANNOT_BE_DROPPED = "CANNOT_BE_DROPPED";
    public static final String CANNOT_BE_SOLD = "CANNOT_BE_SOLD";
    public static final String NO_VALUE = "NO_VALUE";
    public static final String VENDOR_TRASH = "VENDOR_TRASH";

    // --- Locking Logic ---
    public static final String IS_LOCKABLE = "IS_LOCKABLE";

    // --- World Interaction ---
    public static final String IS_SLIDEABLE = "IS_SLIDEABLE";
    public static final String IS_DESTROYABLE = "IS_DESTROYABLE";

    /** A map of all flag groups, used by the UI to dynamically generate checkboxes. */
    public static final Map<String, List<String>> ALL_FLAG_GROUPS = new LinkedHashMap<>();

    static {
        ALL_FLAG_GROUPS.put("Binding & Ownership", Arrays.asList(
                BINDS_ON_EQUIP, BINDS_ON_PICKUP, BINDS_ON_USE, CANNOT_BE_BANKED, UNIQUE, UNIQUE_EQUIPPED
        ));
        ALL_FLAG_GROUPS.put("Durability & Destruction", Arrays.asList(
                CANNOT_BE_REPAIRED, CANNOT_BE_SALVAGED, CONSUMED_ON_USE, DESTROY_ON_DEATH, HAS_DURABILITY, INDESTRUCTIBLE, TEMPORARY_EXPIRES
        ));
        ALL_FLAG_GROUPS.put("Quest, Story, & Cursed", Arrays.asList(
                // Using shared constants now
                STARTS_QUEST, QUEST_ITEM, KEY_ITEM, LORE_ITEM,
                // Object-specific ones remain
                ATTRACTS_AGGRESSION, CURSED, DRAINS_LIFE_MANA, FRAGILE, IS_HIDDEN
        ));
        ALL_FLAG_GROUPS.put("Special & Miscellaneous", Arrays.asList(
                CLASS_SPECIFIC, FACTION_SPECIFIC, RACE_SPECIFIC, REQUIRES_ATTUNEMENT, STACKABLE, UNMODIFIABLE
        ));
        ALL_FLAG_GROUPS.put("Trade & Economy", Arrays.asList(
                CANNOT_BE_DROPPED, CANNOT_BE_SOLD, NO_VALUE, VENDOR_TRASH
        ));
        ALL_FLAG_GROUPS.put("World Interaction", Arrays.asList(
                IS_SLIDEABLE, IS_DESTROYABLE
        ));

        // --- Shared Categories ---
        // Add the shared time flags for use in the Object UI
        ALL_FLAG_GROUPS.putAll(TIME_FLAG_GROUP);
    }

    private ObjectFlagConstants() { /* Prevent instantiation */ }
}