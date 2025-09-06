package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.model.RoomData;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;

public class RoomPreviewPanel extends JPanel {
    private JTextArea previewTextArea;

    public RoomPreviewPanel() {
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Room Preview (Read-Only)"));
        previewTextArea = new JTextArea();
        previewTextArea.setEditable(false);
        // Add the text area to a scroll pane for long descriptions
        add(new JScrollPane(previewTextArea), BorderLayout.CENTER);
        updateRoomPreview(null, null);
    }

    /**
     * FIX: The method now accepts the fileName directly instead of trying to get it from the RoomData object.
     * @param room The room data to display, or null.
     * @param fileName The file name of the room, or null.
     */
    public void updateRoomPreview(RoomData room, String fileName) {
        if (room != null && fileName != null) {
            StringBuilder previewText = new StringBuilder();
            previewText.append("File: ").append(fileName).append("\n");
            previewText.append("Location: ").append(room.getLocationName()).append("\n");
            previewText.append("Area: ").append(room.getAreaName()).append("\n\n");

            previewText.append("----------\n\n");

            previewText.append(room.getRoomName()).append("\n\n");
            previewText.append(room.getShortDescription()).append("\n\n");
            previewText.append(room.getLongDescription()).append("\n\n");

            previewTextArea.setText(previewText.toString());
        } else {
            previewTextArea.setText("No room selected or created yet. Add a room to see its preview here.");
        }
        // Ensure the scroll position resets to the top
        previewTextArea.setCaretPosition(0);
    }
}