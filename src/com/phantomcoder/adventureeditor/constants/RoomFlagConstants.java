package com.phantomcoder.adventureeditor.constants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class RoomFlagConstants {

    // --- Purpose Flags ---
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

    // --- Mechanic Flags ---
    public static final String ANTI_MAGIC_ZONE = "ANTI_MAGIC_ZONE";
    public static final String ITEM_REQUIRED = "ITEM_REQUIRED";
    public static final String PUZZLE_ROOM = "PUZZLE_ROOM";
    public static final String RESPAWN_POINT = "RESPAWN_POINT";
    public static final String SAFE_ZONE = "SAFE_ZONE";
    public static final String TRAP_ROOM = "TRAP_ROOM";

    // --- Environmental Flags ---
    public static final String DARK = "DARK";
    public static final String FREEZING = "FREEZING";
    public static final String LAVA = "LAVA";
    public static final String OUTDOOR = "OUTDOOR";
    public static final String POISONOUS = "POISONOUS";
    public static final String SLIPPERY = "SLIPPERY";
    public static final String WATER = "WATER";

    /** A map of all flag groups, used by the UI to dynamically generate checkboxes. */
    public static final Map<String, List<String>> ALL_FLAG_GROUPS = new LinkedHashMap<>();

    static {
        ALL_FLAG_GROUPS.put("Purpose Flags", Arrays.asList(
                BOSS_ROOM, CHURCH, DUNGEON, GRAVEYARD, HUB_TOWN, MINI_BOSS_ROOM,
                SECRET_ROOM, SHOP, STARTING_POINT, TREASURE_ROOM
        ));
        ALL_FLAG_GROUPS.put("Mechanic Flags", Arrays.asList(
                ANTI_MAGIC_ZONE, ITEM_REQUIRED, PUZZLE_ROOM, RESPAWN_POINT, SAFE_ZONE, TRAP_ROOM
        ));
        ALL_FLAG_GROUPS.put("Environmental Flags", Arrays.asList(
                DARK, FREEZING, LAVA, OUTDOOR, POISONOUS, SLIPPERY, WATER
        ));
    }

    private RoomFlagConstants() { /* Prevent instantiation */ }
}