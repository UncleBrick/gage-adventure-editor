package com.phantomcoder.adventureeditor.constants;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

public final class LayoutConstants {

    // --- General Panel Layouts ---
    public static final Border PANEL_PADDING = new EmptyBorder(10, 10, 10, 10);
    public static final int PANEL_HGAP = 10;
    public static final int PANEL_VGAP = 10;

    // --- Padding Insets ---
    public static final Insets DEFAULT_PADDING = new Insets(5, 5, 5, 5);
    public static final Insets FIELD_PADDING = new Insets(2, 5, 2, 5);
    public static final Insets DIALOG_FIELD_PADDING = new Insets(2, 2, 2, 2);
    public static final Insets TOOLBAR_BUTTON_MARGIN = new Insets(2, 5, 2, 5); // NEW

    // --- Grid Layouts ---
    public static final int ACTIONS_PANEL_ROWS = 1;
    public static final int ACTIONS_PANEL_COLS = 3;
    public static final int OBJECTS_BUTTON_GRID_ROWS = 5;
    public static final int OBJECTS_BUTTON_GRID_COLS = 1;

    // --- FIX: New constants to eliminate magic numbers ---

    // For the vertical JSplitPane in the RoomEditorPanel
    public static final double ROOM_EDITOR_SPLIT_PANE_RESIZE_WEIGHT = .005;
    public static final double ROOM_EDITOR_DIVIDER_LOCATION = .5;

    // For the GridLayout in the MiddleDataPanel
    public static final int BUTTON_PANEL_ROWS = 1;
    public static final int BUTTON_PANEL_COLS = 2;
    public static final int BUTTON_PANEL_HGAP = 10;
    public static final int BUTTON_PANEL_VGAP = 5;

    public static final int BORDER_LAYOUT_HGAP = 5;
    public static final int BORDER_LAYOUT_VGAP = 5;

    private LayoutConstants() { /* Prevent instantiation */ }
}