package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.config.AppConfig;
import com.phantomcoder.adventureeditor.constants.FontConstants;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.util.PathUtil;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("serial")
public class RoomPreviewPanel extends JPanel {

    private JTextArea roomPreviewArea;

    public RoomPreviewPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Room Preview (Read-Only)"));

        roomPreviewArea = new JTextArea();
        roomPreviewArea.setEditable(false);
        roomPreviewArea.setLineWrap(true);
        roomPreviewArea.setWrapStyleWord(true);
        roomPreviewArea.setFont(new Font(
                FontConstants.PREVIEW_FONT_NAME,
                FontConstants.PLAIN_STYLE,
                FontConstants.PREVIEW_FONT_SIZE
        ));

        JScrollPane scrollPane = new JScrollPane(roomPreviewArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateRoomPreview(RoomData room) {
        StringBuilder previewText = new StringBuilder();
        if (room == null) {
            previewText.append("No room selected or created yet. Add a room to see its preview here.");
        } else {
            previewText.append("--- ").append(room.getRoomName()).append(" ---\n\n");
            previewText.append(room.getShortDescription()).append("\n\n");
            previewText.append(room.getLongDescription()).append("\n\n");
        }
        if (AppConfig.isDebuggingEnabled()) {
            previewText.append("\n\n--- Debug Info ---\n");
            if (room != null) {
                try {
                    Path relativeBasePath = Paths.get("resources");
                    String safeLocationName = PathUtil.toOsSafeLowerCase(room.getLocationName());
                    String safeAreaName = PathUtil.toOsSafeLowerCase(room.getAreaName());
                    String safeFileName = PathUtil.toOsSafeLowerCase(room.getFilename());
                    Path constructedRelativePath = Paths.get("data", "rooms", safeLocationName, safeAreaName, safeFileName + ".json");
                    previewText.append("Relative Save Path: ").append(relativeBasePath.resolve(constructedRelativePath).toString()).append("\n");
                } catch (Exception e) {
                    previewText.append("Error generating debug path: ").append(e.getMessage()).append("\n");
                }
            } else {
                previewText.append("No room loaded to show path information.\n");
            }
            previewText.append("-----------------\n");
        }
        roomPreviewArea.setText(previewText.toString());
        roomPreviewArea.setCaretPosition(0);
    }
}