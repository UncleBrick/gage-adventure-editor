package com.phantomcoder.adventureeditor.constants;

import java.awt.Dimension;

public final class DialogConstants {

    // --- General Dialog Options ---
    public static final int CONTINUE_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    // --- AmbianceManagerDialog ---
    public static final Dimension AMBIANCE_DIALOG_SIZE = new Dimension(800, 300);
    public static final int AMBIANCE_ID_COL_WIDTH = 150;
    public static final int AMBIANCE_GUID_COL_WIDTH = 250;
    public static final int AMBIANCE_TEXT_COL_WIDTH = 300;
    public static final int AMBIANCE_FREQ_COL_WIDTH = 80;

    // --- SaveWarningDialog ---
    public static final int SAVE_WARNING_LIST_WIDTH = 250;
    public static final int SAVE_WARNING_LIST_HEIGHT = 80;

    private DialogConstants() { /* Prevent instantiation */ }
}