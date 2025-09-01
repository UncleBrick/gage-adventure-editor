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
    private final ActionButtonsPanel actionButtonsPanel;
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

        // --- THE FIX IS HERE ---
        // 1. Initialize actionManager BEFORE it is used.
        // 2. Corrected the typo from getAction-Manager() to getActionManager()
        ActionManager actionManager = roomController.getActionManager();

        actionButtonsPanel = new ActionButtonsPanel(
                actionManager.newAction,
                actionManager.loadAction,
                actionManager.saveAction
        );
        // --- END OF FIX ---

        gbc.gridy = 0;
        gbc.weighty = 0.0;
        add(topMetaDataPanel, gbc);

        gbc.gridy = 1;
        add(middleDataPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(longDescriptionPanel, gbc);

        gbc.gridy = 3;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(actionButtonsPanel, gbc);
    }

    public TopMetaDataPanel getTopMetaDataPanel() { return topMetaDataPanel; }
    public MiddleDataPanel getMiddleDataPanel() { return middleDataPanel; }
    public LongDescriptionPanel getLongDescriptionPanel() { return longDescriptionPanel; }
    public RoomController getRoomController() { return roomController; }
}