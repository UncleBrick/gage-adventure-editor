package com.phantomcoder.adventureeditor.constants;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.InputStream;
import java.util.Objects;

public final class FrameConstants {
    // --- Frame Sizes ---
    public static final int MAIN_FRAME_WIDTH = 875;
    public static final int MAIN_FRAME_HEIGHT = 800;
    public static final int PREVIEW_FRAME_WIDTH = 500;
    public static final int PREVIEW_FRAME_HEIGHT = 800;
    public static final int AMBIANCE_DIALOG_WIDTH = 600;
    public static final int AMBIANCE_DIALOG_HEIGHT = 400;

    // --- Window Properties ---
    public static final String MAIN_WINDOW_TITLE = "Generic Adventure Game Editor (GAGE)";
    public static final Image PROGRAM_ICON = loadIcon();

    private static Image loadIcon() {
        try {
            try (InputStream stream = FrameConstants.class.getResourceAsStream("/icons/gage_icon.png")) {
                return ImageIO.read(Objects.requireNonNull(stream));
            }
        } catch (Exception e) {
            System.err.println("Error: Could not load application icon from FrameConstants.");
            e.printStackTrace();
            return null;
        }
    }

    private FrameConstants() { /* Prevent instantiation */ }
}