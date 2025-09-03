package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.controller.ActionManager;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.gui.GameEditorFrame;
import com.phantomcoder.adventureeditor.service.IRoomService;
import com.phantomcoder.adventureeditor.service.RoomService;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class RoomEditorPanel extends JPanel {

    private final TopMetaDataPanel topMetaDataPanel;
    private final MiddleDataPanel middleDataPanel;
    private final LongDescriptionPanel longDescriptionPanel;
    // REMOVED: The ActionButtonsPanel is no longer needed here
    private final RoomController roomController;

    public RoomEditorPanel(GameEditorFrame parentFrame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        topMetaDataPanel = new TopMetaDataPanel();
        middleDataPanel = new MiddleDataPanel();
        longDescriptionPanel = new LongDescriptionPanel();

        IRoomService roomService = new RoomService(middleDataPanel, longDescriptionPanel, topMetaDataPanel);
        roomController = new RoomController(this, parentFrame, roomService);

        // --- REMOVED ---
        // The ActionManager and ActionButtonsPanel are no longer created or added here
        // --- END REMOVED ---

        // ASSEMBLE THE UI
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        add(topMetaDataPanel, gbc);

        gbc.gridy = 1;
        add(middleDataPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(longDescriptionPanel, gbc);

        // The ActionButtonsPanel is no longer added to the layout
    }

    public TopMetaDataPanel getTopMetaDataPanel() { return topMetaDataPanel; }
    public MiddleDataPanel getMiddleDataPanel() { return middleDataPanel; }
    public LongDescriptionPanel getLongDescriptionPanel() { return longDescriptionPanel; }
    public RoomController getRoomController() { return roomController; }
}