package com.phantomcoder.adventureeditor.controller;

import com.phantomcoder.adventureeditor.gui.panels.ObjectsPanel;
import com.phantomcoder.adventureeditor.model.RoomData;
import javax.swing.JFrame;

public class ObjectController {

    private final JFrame parentFrame;
    private final ObjectsPanel objectsPanel;

    public ObjectController(JFrame parentFrame, ObjectsPanel objectsPanel) {
        this.parentFrame = parentFrame;
        this.objectsPanel = objectsPanel;
    }

    public void wireUpButtons() {
        // Logic to add ActionListeners to the buttons on the ObjectsPanel
        // e.g., objectsPanel.getAddButton().addActionListener(...)
    }

    public void onRoomLoaded(RoomData currentRoom) {
        // Logic to clear and load the JTree with objects for the current room
    }

    public void onNewRoom() {
        // Logic to clear the JTree when a new room is created
    }
}