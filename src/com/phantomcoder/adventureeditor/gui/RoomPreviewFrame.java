package com.phantomcoder.adventureeditor.gui;

import com.phantomcoder.adventureeditor.constants.FrameConstants;
import com.phantomcoder.adventureeditor.gui.panels.RoomPreviewPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RoomPreviewFrame extends JFrame {

    private final RoomPreviewPanel roomPreviewPanel;

    public RoomPreviewFrame(Runnable onWindowCloseAction) {
        setTitle("Room Preview");
        setSize(FrameConstants.PREVIEW_FRAME_WIDTH, FrameConstants.PREVIEW_FRAME_HEIGHT);
        setIconImage(FrameConstants.PROGRAM_ICON);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Add a listener to act when the user clicks the 'X' button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Execute the action provided by the controller
                if (onWindowCloseAction != null) {
                    onWindowCloseAction.run();
                }
            }
        });

        roomPreviewPanel = new RoomPreviewPanel();
        getContentPane().add(roomPreviewPanel, BorderLayout.CENTER);
    }

    public RoomPreviewPanel getRoomPreviewPanel() {
        return roomPreviewPanel;
    }
}