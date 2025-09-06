package com.phantomcoder.adventureeditor.constants;

public final class FieldDefaults {
    public static final String DEFAULT_ROOM_NAME = "Starting Area";
    public static final String DEFAULT_ROOM_DESCRIPTION = "A dimly lit room."; // Short Description
    public static final String DEFAULT_LONG_DESCRIPTION = "This is a detailed description of a new room. You can add more text here up to the character limit.";
    public static final String DEFAULT_LOCATION_NAME = "Dungeon";
    public static final String DEFAULT_AREA_NAME = "Level 1";
    public static final String DEFAULT_FILE_NAME = "room_data";

    // Character limits
    public static final int MAX_LOCATION_NAME_CHARS = 50;
    public static final int MAX_AREA_NAME_CHARS = 50;
    public static final int MAX_FILE_NAME_CHARS = 50;
    public static final int MAX_ROOM_NAME_CHARS = 50;
    public static final int MAX_SHORT_DESC_CHARS = 100;
    public static final int MAX_LONG_DESC_CHARS = 1024;

    // Text Component Column/Row Counts
    public static final int OBJECT_NAME_COLUMNS = 15;
    public static final int OBJECT_SHORT_DESC_COLUMNS = 15;
    public static final int OBJECT_LONG_DESC_ROWS = 4;
    public static final int OBJECT_LONG_DESC_COLS = 30;
    public static final int KEY_ID_COLUMNS = 20;
    public static final int AMBIANCE_ID_COLUMNS = 20;
    public static final int AMBIANCE_TEXT_ROWS = 5;
    public static final int AMBIANCE_TEXT_COLS = 20;

    // For the JTextArea in the AddEditAmbianceDialog
    public static final int AMBIANCE_TEXT_AREA_ROWS = 5;
    public static final int AMBIANCE_TEXT_AREA_COLS = 40;

    // For the JSpinner in the AddEditAmbianceDialog
    public static final int DEFAULT_FREQUENCY = 50;
    public static final int MIN_FREQUENCY = 0;
    public static final int MAX_FREQUENCY = 100;
    public static final int FREQUENCY_STEP = 1;

    private FieldDefaults() { /* Prevent instantiation */ }
}