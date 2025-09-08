package com.phantomcoder.adventureeditor.controller;

import com.phantomcoder.adventureeditor.gui.MainApplicationFrame;
import com.phantomcoder.adventureeditor.gui.panels.RoomEditorPanel;
import com.phantomcoder.adventureeditor.gui.panels.TopMetaDataPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.service.IRoomService;
import com.phantomcoder.adventureeditor.util.DirtyStateListener;
import com.phantomcoder.adventureeditor.util.DirtyStateManager;
import com.phantomcoder.adventureeditor.util.RoomFileChooser;
import com.phantomcoder.adventureeditor.util.UiHelper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import javax.swing.JCheckBoxMenuItem;

public class RoomController {

    private final RoomEditorPanel roomEditorPanel;
    private final MainApplicationFrame parentFrame;
    private final ActionManager actionManager;
    private final IRoomService roomService;
    private final DirtyStateManager dirtyStateManager;
    private ObjectController objectController;
    private JCheckBoxMenuItem previewMenuItem;
    private boolean isRoomDirty = false;

    public RoomController(RoomEditorPanel roomEditorPanel, MainApplicationFrame parentFrame, IRoomService roomService) {
        this.roomEditorPanel = roomEditorPanel;
        this.parentFrame = parentFrame;
        this.actionManager = new ActionManager(this, parentFrame);
        this.roomService = roomService;
        this.dirtyStateManager = new DirtyStateManager();

        attachChangeListeners();
        handleAddRoomAction();
    }

    public void setObjectController(ObjectController objectController) {
        this.objectController = objectController;
    }

    private void attachChangeListeners() {
        DirtyStateListener.applyTo(this.roomEditorPanel, this::onFormChanged);
    }

    private void onFormChanged() {
        roomService.gatherDataFromUI();
        boolean dirty = dirtyStateManager.isDirty(roomService.getCurrentRoom());
        setRoomDirty(dirty);
    }

    private void setRoomDirty(boolean dirty) {
        this.isRoomDirty = dirty;
        updateActionStates();
    }

    private boolean isMetadataValid() {
        TopMetaDataPanel metaDataPanel = roomEditorPanel.getTopMetaDataPanel();
        return !metaDataPanel.getLocationName().trim().isEmpty() &&
                !metaDataPanel.getAreaName().trim().isEmpty() &&
                !metaDataPanel.getFileName().trim().isEmpty();
    }

    private void updateActionStates() {
        boolean hasPath = (roomService.getSavedRoomFilePath() != null);
        actionManager.saveAction.setEnabled(isRoomDirty && isMetadataValid());
        actionManager.saveAsAction.setEnabled(hasPath);
        actionManager.closeAction.setEnabled(hasPath);
    }

    public void handleAddRoomAction() {
        if (isRoomDirty) {
            // TODO: Implement "Warn on close" logic
        }
        roomService.createAndSetCurrentRoom();
        roomService.populateUIFromCurrentRoom();
        dirtyStateManager.takeSnapshot(roomService.getCurrentRoom());
        setRoomDirty(false);
        parentFrame.setStatus("New room created. Fill in the details to save.");
    }

    public void handleLoadRoomAction() {
        if (isRoomDirty) {
            // TODO: Implement "Warn on close" logic
        }

        Optional<Path> result = RoomFileChooser.showLoadRoomDialog(parentFrame);
        if (result.isPresent()) {
            try {
                roomService.loadRoomAndPopulateUI(result.get());
                RoomData currentRoom = roomService.getCurrentRoom();
                dirtyStateManager.takeSnapshot(currentRoom);
                setRoomDirty(false);
                parentFrame.setStatus("Successfully loaded room: " + result.get().getFileName());
            } catch (IOException ex) {
                UiHelper.showErrorDialog(parentFrame, "Load Error", "Failed to load room from file: " + ex.getMessage());
            }
        }
    }

    public void handleSaveCurrentRoomAction() {
        try {
            roomService.gatherDataFromUI();
            roomService.saveCurrentRoom();
            dirtyStateManager.takeSnapshot(roomService.getCurrentRoom());
            setRoomDirty(false);
            parentFrame.setStatus("Successfully saved room: " + roomService.getSavedRoomFilePath().getFileName());
        } catch (IllegalArgumentException | IOException ex) {
            UiHelper.showErrorDialog(parentFrame, "Save Error", "Failed to save room: " + ex.getMessage());
        }
    }

    public void handleManageAmbianceAction() {
        // This logic will be implemented later
    }

    public void updateAmbianceData(List<AmbianceEvent> events) {
        roomService.setCurrentAmbianceEvents(events);
        onFormChanged();
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public void setPreviewMenuItem(JCheckBoxMenuItem previewMenuItem) {
        this.previewMenuItem = previewMenuItem;
    }
}