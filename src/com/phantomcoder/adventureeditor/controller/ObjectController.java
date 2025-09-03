package com.phantomcoder.adventureeditor.controller;

import com.phantomcoder.adventureeditor.gui.GameEditorFrame;
import com.phantomcoder.adventureeditor.gui.dialogs.AddEditObjectDialog;
import com.phantomcoder.adventureeditor.gui.panels.ObjectsPanel;
import com.phantomcoder.adventureeditor.model.RoomData;

/**
 * Controller responsible for all logic and user actions related to the ObjectsPanel.
 */
public class ObjectController {

    private final GameEditorFrame parentFrame;
    private final ObjectsPanel objectsPanel;
    private RoomData currentRoom; // Keep track of the currently loaded room

    public ObjectController(GameEditorFrame parentFrame, ObjectsPanel objectsPanel) {
        this.parentFrame = parentFrame;
        this.objectsPanel = objectsPanel;
    }

    /**
     * Wires up the ActionListeners for the buttons on the ObjectsPanel.
     * This is called from the GameEditorFrame during setup.
     */
    public void wireUpButtons() {
        objectsPanel.getAddButton().addActionListener(e -> handleAddObjectAction());
        objectsPanel.getEditButton().addActionListener(e -> handleEditObjectAction());
        // We will add listeners for Remove and Move buttons later.
    }

    /**
     * Called by the RoomController when a new room is loaded.
     * @param room The RoomData for the newly loaded room.
     */
    public void onRoomLoaded(RoomData room) {
        this.currentRoom = room;
        if (room != null) {
            objectsPanel.updateTitle(room.getLocationName(), room.getAreaName(), room.getRoomName());
            // Later, we will also load the objects for this room here.
        }
    }

    /**
     * Called by the RoomController when the "New Room" action is triggered.
     */
    public void onNewRoom() {
        this.currentRoom = null;
        objectsPanel.resetTitle();
        // Later, we will also clear the JTree of objects here.
    }

    private void handleAddObjectAction() {
        if (currentRoom == null) {
            // Optionally show an error message that a room must be loaded first
            return;
        }
        // Use the currentRoom's data to populate the dialog metadata
        AddEditObjectDialog dialog = new AddEditObjectDialog(
                parentFrame,
                currentRoom.getLocationName(),
                currentRoom.getAreaName(),
                currentRoom.getFilename()
        );
        dialog.setVisible(true);
        // We will add logic here to get the result and add it to the JTree
    }

    private void handleEditObjectAction() {
        if (currentRoom == null) {
            return;
        }
        // Logic to get the selected object from the JTree will go here

        AddEditObjectDialog dialog = new AddEditObjectDialog(
                parentFrame,
                currentRoom.getLocationName(),
                currentRoom.getAreaName(),
                currentRoom.getFilename()
        );
        // We will add logic here to pre-fill the dialog with the object's data
        dialog.setVisible(true);
        // We will add logic here to get the result and update the JTree
    }
}