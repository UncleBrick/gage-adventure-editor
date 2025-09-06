package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.gui.MainApplicationFrame;
import com.phantomcoder.adventureeditor.service.IRoomService;
import com.phantomcoder.adventureeditor.service.RoomService;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class RoomEditorPanel extends JPanel {

    private final RoomController roomController;
    private final TopMetaDataPanel topMetaDataPanel;
    private final MiddleDataPanel middleDataPanel;
    private final LongDescriptionPanel longDescriptionPanel;

    public RoomEditorPanel(MainApplicationFrame parentFrame) {
        setLayout(new BorderLayout());

        this.topMetaDataPanel = new TopMetaDataPanel();
        this.middleDataPanel = new MiddleDataPanel();
        this.longDescriptionPanel = new LongDescriptionPanel();

        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(topMetaDataPanel, BorderLayout.NORTH);
        upperPanel.add(middleDataPanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPanel, longDescriptionPanel);
        splitPane.setResizeWeight(LayoutConstants.ROOM_EDITOR_SPLIT_PANE_RESIZE_WEIGHT);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);

        EventQueue.invokeLater(() -> splitPane.setDividerLocation(upperPanel.getPreferredSize().height));

        // FIX: Added 'this' as the first argument to match the constructor.
        IRoomService roomService = new RoomService(this, middleDataPanel, longDescriptionPanel, topMetaDataPanel);
        this.roomController = new RoomController(this, parentFrame, roomService);
    }

    public RoomController getRoomController() {
        return roomController;
    }

    public TopMetaDataPanel getTopMetaDataPanel() {
        return this.topMetaDataPanel;
    }

    public MiddleDataPanel getMiddleDataPanel() {
        return this.middleDataPanel;
    }

    public LongDescriptionPanel getLongDescriptionPanel() {
        return this.longDescriptionPanel;
    }
}