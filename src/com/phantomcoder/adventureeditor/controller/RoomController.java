package com.phantomcoder.adventureeditor.controller;

import com.phantomcoder.adventureeditor.gui.MainApplicationFrame;
import com.phantomcoder.adventureeditor.gui.dialogs.AmbianceManagerDialog;
import com.phantomcoder.adventureeditor.gui.panels.LongDescriptionPanel;
import com.phantomcoder.adventureeditor.gui.panels.MiddleDataPanel;
import com.phantomcoder.adventureeditor.gui.panels.RoomEditorPanel;
import com.phantomcoder.adventureeditor.gui.panels.TopMetaDataPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.service.IRoomService;
import com.phantomcoder.adventureeditor.util.RoomFileChooser;
import com.phantomcoder.adventureeditor.util.UiHelper;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RoomController {

    private final RoomEditorPanel roomEditorPanel;
    private final MainApplicationFrame parentFrame;
    private final ActionManager actionManager;
    private final IRoomService roomService;
    private ObjectController objectController;
    private AmbianceManagerDialog ambianceManagerDialog;
    private boolean isRoomDirty = false;
    private boolean isRoomActive = false;

    public RoomController(RoomEditorPanel roomEditorPanel, MainApplicationFrame parentFrame, IRoomService roomService) {
        this.roomEditorPanel = roomEditorPanel;
        this.parentFrame = parentFrame;
        this.actionManager = new ActionManager(this, parentFrame);
        this.roomService = roomService;
        attachChangeListeners();
        updateRoomStateActions();
    }

    public void setObjectController(ObjectController objectController) {
        this.objectController = objectController;
    }

    private void attachChangeListeners() {
        DocumentListener dirtyListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { setRoomDirty(true); }
            @Override public void removeUpdate(DocumentEvent e) { setRoomDirty(true); }
            @Override public void changedUpdate(DocumentEvent e) { setRoomDirty(true); }
        };
        ItemListener dirtyItemListener = e -> setRoomDirty(true);
        roomEditorPanel.getTopMetaDataPanel().addChangeListener(dirtyListener);
        roomEditorPanel.getLongDescriptionPanel().addChangeListener(dirtyListener);
        MiddleDataPanel middleDataPanel = roomEditorPanel.getMiddleDataPanel();
        middleDataPanel.addChangeListener(dirtyListener, dirtyItemListener);
        middleDataPanel.getManageAmbianceButton().addActionListener(e -> handleManageAmbianceAction());
    }

    private void setRoomDirty(boolean dirty) {
        if (isRoomDirty == dirty) return;
        isRoomDirty = dirty;
        updateRoomStateActions();
    }

    private void updateRoomStateActions() {
        boolean metadataValid = isMetadataValid();
        actionManager.saveAction.setEnabled(isRoomDirty && metadataValid);
        actionManager.newAction.setEnabled(isRoomDirty || isRoomActive);
        actionManager.saveAsAction.setEnabled(isRoomActive);
        actionManager.closeAction.setEnabled(isRoomActive);
        MiddleDataPanel middleDataPanel = roomEditorPanel.getMiddleDataPanel();
        middleDataPanel.getManageAmbianceButton().setEnabled(isRoomActive && metadataValid);
        middleDataPanel.getManageTimeStatesButton().setEnabled(isRoomActive && metadataValid);
    }

    private boolean isMetadataValid() {
        TopMetaDataPanel metaDataPanel = roomEditorPanel.getTopMetaDataPanel();
        return !metaDataPanel.getLocationName().trim().isEmpty() &&
                !metaDataPanel.getAreaName().trim().isEmpty() &&
                !metaDataPanel.getFileName().trim().isEmpty();
    }

    public void handleLoadRoomAction() {
        Optional<Path> selectedFile = RoomFileChooser.showLoadRoomDialog(parentFrame);
        if (selectedFile.isPresent()) {
            Path filePath = selectedFile.get();
            try {
                roomService.loadRoomAndPopulateUI(filePath);
                RoomData currentRoom = roomService.getCurrentRoom();
                if (objectController != null) {
                    objectController.onRoomLoaded(currentRoom);
                }
                parentFrame.setStatus("Successfully loaded room: " + filePath.getFileName());
                parentFrame.updatePreview(currentRoom, filePath.getFileName().toString());
                isRoomActive = true;
                setRoomDirty(false);
            } catch (IOException e) {
                UiHelper.showErrorDialog(parentFrame, "Load Error", "Error loading room file:\n" + e.getMessage());
                parentFrame.setStatus("Failed to load room.");
            }
        }
    }

    public void handleSaveCurrentRoomAction() {
        try {
            roomService.saveCurrentRoom();
            parentFrame.setStatus("Successfully saved room: " + roomService.getSavedRoomFilePath());
            isRoomActive = true;
            setRoomDirty(false);
        } catch (IllegalArgumentException | IOException e) {
            UiHelper.showErrorDialog(parentFrame, "Save Error", "Error saving room file:\n" + e.getMessage());
            parentFrame.setStatus("Failed to save room.");
        }
    }

    public void handleAddRoomAction() {
        roomEditorPanel.getTopMetaDataPanel().setLocationName("");
        roomEditorPanel.getTopMetaDataPanel().setAreaName("");
        roomEditorPanel.getTopMetaDataPanel().setFileName("");
        roomEditorPanel.getMiddleDataPanel().setRoomName("");
        roomEditorPanel.getMiddleDataPanel().setShortDescription("");
        roomEditorPanel.getMiddleDataPanel().getRoomFlagsPanel().setSelectedTags(Collections.emptySet());
        roomEditorPanel.getLongDescriptionPanel().setLongDescription("");
        roomService.setCurrentAmbianceEvents(Collections.emptyList());
        if (objectController != null) {
            objectController.onNewRoom();
        }
        isRoomActive = false;
        setRoomDirty(false);
        parentFrame.setStatus("New room form cleared. Fill in the details and click Save.");
        parentFrame.updatePreview(null, null);
    }

    private void handleManageAmbianceAction() {
        if (ambianceManagerDialog == null) {
            ambianceManagerDialog = new AmbianceManagerDialog(parentFrame, this);
        }
        ambianceManagerDialog.displayEvents(roomService.getCurrentAmbianceEvents());
        ambianceManagerDialog.setVisible(true);
    }

    public void updateAmbianceData(List<AmbianceEvent> updatedEvents) {
        if (!new HashSet<>(roomService.getCurrentAmbianceEvents()).equals(new HashSet<>(updatedEvents))) {
            roomService.setCurrentAmbianceEvents(updatedEvents);
            setRoomDirty(true);
        }
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public void setPreviewMenuItem(JCheckBoxMenuItem item) {
        // This functionality is now handled by the MainApplicationFrame and ActionManager
    }

    public void handleConfigureAction() {
        parentFrame.setStatus("Game Configuration action not yet implemented.");
    }

    public void handleHelpAction() {
        parentFrame.setStatus("Help action not yet implemented.");
    }

    public void handleAboutAction() {
        parentFrame.setStatus("About action not yet implemented.");
    }
}