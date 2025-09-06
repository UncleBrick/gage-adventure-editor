package com.phantomcoder.adventureeditor.constants;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RoomFlagConstants {

    // --- Individual Flag Constants (Existing) ---
    // Purpose Flags
    public static final String BOSS_ROOM = "BOSS_ROOM";
    public static final String CHURCH = "CHURCH";
    public static final String DUNGEON = "DUNGEON";
    public static final String GRAVEYARD = "GRAVEYARD";
    public static final String HUB_TOWN = "HUB_TOWN";
    public static final String MINI_BOSS_ROOM = "MINI_BOSS_ROOM";
    public static final String SECRET_ROOM = "SECRET_ROOM";
    public static final String SHOP = "SHOP";
    public static final String STARTING_POINT = "STARTING_POINT";
    public static final String TREASURE_ROOM = "TREASURE_ROOM";

    // Mechanic Flags
    public static final String ANTI_MAGIC_ZONE = "ANTI_MAGIC_ZONE";
    public static final String ITEM_REQUIRED = "ITEM_REQUIRED";
    public static final String PUZZLE_ROOM = "PUZZLE_ROOM";
    public static final String RESPAWN_POINT = "RESPAWN_POINT";
    public static final String SAFE_ZONE = "SAFE_ZONE";
    public static final String TRAP_ROOM = "TRAP_ROOM";

    // Environmental Flags
    public static final String DARK = "DARK";
    public static final String FREEZING = "FREEZING";
    public static final String LAVA = "LAVA";
    public static final String OUTDOOR = "OUTDOOR";
    public static final String POISONOUS = "POISONOUS";
    public static final String SLIPPERY = "SLIPPERY";
    public static final String FLOODED = "FLOODED";


    // --- FIX: Add the categorized map for dynamic UI generation ---
    public static final Map<String, List<String>> CATEGORIZED_FLAGS;

    static {
        // Using a LinkedHashMap preserves the insertion order for the tabs.
        Map<String, List<String>> map = new LinkedHashMap<>();

        map.put("Purpose Flags", List.of(
                BOSS_ROOM, CHURCH, DUNGEON, GRAVEYARD, HUB_TOWN, MINI_BOSS_ROOM,
                SECRET_ROOM, SHOP, STARTING_POINT, TREASURE_ROOM
        ));

        map.put("Mechanic Flags", List.of(
                ANTI_MAGIC_ZONE, ITEM_REQUIRED, PUZZLE_ROOM, RESPAWN_POINT,
                SAFE_ZONE, TRAP_ROOM
        ));

        map.put("Environmental Flags", List.of(
                DARK, FREEZING, LAVA, OUTDOOR, POISONOUS, SLIPPERY, FLOODED
        ));

        // Make the map unmodifiable so it's a true constant.
        CATEGORIZED_FLAGS = Collections.unmodifiableMap(map);
    }
}