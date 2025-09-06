package com.phantomcoder.adventureeditor.gui.panels;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * The main container panel for the room preview. This panel is intended to be
 * placed on the right side of the application's main JSplitPane.
 * This class is a refactoring of the original RoomPreviewFrame.
 */
public class PreviewPanel extends JPanel {

    private final RoomPreviewPanel roomPreviewPanel;

    public PreviewPanel() {
        // Set the layout for this container panel
        setLayout(new BorderLayout());

        // Create the inner panel that actually draws the preview content
        this.roomPreviewPanel = new RoomPreviewPanel();

        // Add the inner panel to fill this container
        add(roomPreviewPanel, BorderLayout.CENTER);
    }

    /**
     * Provides access to the inner panel so the controller can send it
     * updated room data to display.
     * @return The instance of the RoomPreviewPanel.
     */
    public RoomPreviewPanel getRoomPreviewPanel() {
        return roomPreviewPanel;
    }
}