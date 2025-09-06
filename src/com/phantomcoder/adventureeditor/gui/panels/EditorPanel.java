package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.controller.ObjectController;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.gui.MainApplicationFrame;
import com.phantomcoder.adventureeditor.util.UiHelper;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EditorPanel extends JPanel {

    private final RoomController roomController;

    public EditorPanel(MainApplicationFrame parentFrame) {
        setLayout(new BorderLayout());

        RoomEditorPanel roomEditorPanel = new RoomEditorPanel(parentFrame);
        this.roomController = roomEditorPanel.getRoomController();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        ObjectsPanel objectsPanel = new ObjectsPanel();
        ObjectController objectController = new ObjectController(parentFrame, objectsPanel);
        objectController.wireUpButtons();

        roomController.setObjectController(objectController);

        tabbedPane.addTab("Rooms", roomEditorPanel);
        tabbedPane.addTab("Objects", objectsPanel);
        tabbedPane.addTab("NPCs", new JPanel());
        tabbedPane.addTab("Quests", new JPanel());
        tabbedPane.addTab("Exits", new JPanel());
        tabbedPane.addTab("Commands", new JPanel());
        tabbedPane.addTab("Dialogue", new JPanel());
        UiHelper.styleTabbedPane(tabbedPane);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public RoomController getRoomController() {
        return this.roomController;
    }
}