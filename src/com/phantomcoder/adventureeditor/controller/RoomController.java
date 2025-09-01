package com.phantomcoder.adventureeditor.controller;

import com.phantomcoder.adventureeditor.constants.DataConstants;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.gui.GameEditorFrame;
import com.phantomcoder.adventureeditor.gui.RoomPreviewFrame;
import com.phantomcoder.adventureeditor.gui.dialogs.AddEditObjectDialog;
import com.phantomcoder.adventureeditor.gui.dialogs.AmbianceManagerDialog;
import com.phantomcoder.adventureeditor.gui.panels.MiddleDataPanel;
import com.phantomcoder.adventureeditor.gui.panels.ObjectsPanel;
import com.phantomcoder.adventureeditor.gui.panels.RoomEditorPanel;
import com.phantomcoder.adventureeditor.gui.panels.TopMetaDataPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.service.IRoomService;
import com.phantomcoder.adventureeditor.util.RoomFileChooser;
import com.phantomcoder.adventureeditor.util.UiHelper;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class RoomController {

    private final RoomEditorPanel roomEditorPanel;
    private final GameEditorFrame parentFrame;
    private RoomPreviewFrame roomPreviewFrame;
    private JCheckBoxMenuItem previewMenuItem;
    private final ActionManager actionManager;
    private ObjectsPanel objectsPanel;
    private final IRoomService roomService;
    private AmbianceManagerDialog ambianceManagerDialog;
    private boolean isRoomDirty = false;
    private boolean isRoomActive = false;

    public RoomController(RoomEditorPanel roomEditorPanel, GameEditorFrame parentFrame, IRoomService roomService) {
        this.roomEditorPanel = roomEditorPanel;
        this.parentFrame = parentFrame;
        this.actionManager = new ActionManager(this);
        this.roomService = roomService;
        attachChangeListeners();
        updateRoomStateActions();
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
        // This call remains the same, but the new MiddleDataPanel now correctly
        // delegates the itemListener to the RoomFlagsPanel.
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
        roomEditorPanel.getMiddleDataPanel().getManageAmbianceButton().setEnabled(isRoomActive && metadataValid);
    }

    private boolean isMetadataValid() {
        TopMetaDataPanel metaDataPanel = roomEditorPanel.getTopMetaDataPanel();
        return !metaDataPanel.getLocationName().trim().isEmpty() &&
                !metaDataPanel.getAreaName().trim().isEmpty() &&
                !metaDataPanel.getFileName().trim().isEmpty();
    }

    public void setObjectsPanel(ObjectsPanel objectsPanel) {
        this.objectsPanel = objectsPanel;
        this.objectsPanel.getAddButton().addActionListener(e -> handleAddObjectAction());
        this.objectsPanel.getEditButton().addActionListener(e -> handleEditObjectAction());
    }

    public void handleLoadRoomAction() {
        Optional<Path> selectedFile = RoomFileChooser.showLoadRoomDialog(parentFrame);

        if (selectedFile.isPresent()) {
            Path filePath = selectedFile.get();

            String requiredSegment = File.separator + DataConstants.ROOMS_DIRECTORY_NAME + File.separator;
            if (!filePath.toString().contains(requiredSegment)) {
                UiHelper.showErrorDialog(parentFrame,
                        "Invalid File Location",
                        "Please select a valid room file from within a '" + DataConstants.ROOMS_DIRECTORY_NAME + "' folder.");
                return;
            }

            try {
                roomService.loadRoomAndPopulateUI(filePath);
                RoomData currentRoom = roomService.getCurrentRoom();
                if (objectsPanel != null && currentRoom != null) {
                    objectsPanel.updateTitle(currentRoom.getLocationName(), currentRoom.getAreaName(), currentRoom.getRoomName());
                }
                if (roomPreviewFrame != null && roomPreviewFrame.isVisible()) {
                    roomPreviewFrame.getRoomPreviewPanel().updateRoomPreview(currentRoom);
                }
                parentFrame.setStatus("Successfully loaded room: " + filePath.getFileName());
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
        // Use the simplified MiddleDataPanel setters
        roomEditorPanel.getMiddleDataPanel().setRoomName("");
        roomEditorPanel.getMiddleDataPanel().setShortDescription("");
        // Clear tags via the new RoomFlagsPanel
        roomEditorPanel.getMiddleDataPanel().getRoomFlagsPanel().setSelectedTags(Collections.emptySet());
        roomEditorPanel.getLongDescriptionPanel().setLongDescription("");
        roomService.setCurrentAmbianceEvents(Collections.emptyList());
        if (objectsPanel != null) {
            objectsPanel.resetTitle();
        }
        isRoomActive = false;
        setRoomDirty(false);
        parentFrame.setStatus("New room form cleared. Fill in the details and click Save.");
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

    public ActionManager getActionManager() { return actionManager; }
    public void setPreviewMenuItem(JCheckBoxMenuItem item) { this.previewMenuItem = item; }

    public void togglePreviewWindow() {
        if (roomPreviewFrame == null) {
            roomPreviewFrame = new RoomPreviewFrame(this::handlePreviewWindowClosed);
        }
        boolean shouldBeVisible = !roomPreviewFrame.isVisible();
        if (shouldBeVisible) {
            roomPreviewFrame.getRoomPreviewPanel().updateRoomPreview(roomService.getCurrentRoom());
            Rectangle screenBounds = parentFrame.getGraphicsConfiguration().getBounds();
            int totalWidth = parentFrame.getWidth() + roomPreviewFrame.getWidth();
            int startX = screenBounds.x + (screenBounds.width - totalWidth) / 2;
            int startY = screenBounds.y + (screenBounds.height - parentFrame.getHeight()) / 2;
            parentFrame.setLocation(startX, startY);
            roomPreviewFrame.setLocation(startX + parentFrame.getWidth() + LayoutConstants.PREVIEW_DOCK_OFFSET, startY);
            roomPreviewFrame.setVisible(true);
            if (previewMenuItem != null) {
                previewMenuItem.setSelected(true);
            }
        } else {
            roomPreviewFrame.setVisible(false);
            handlePreviewWindowClosed();
        }
    }

    private void handlePreviewWindowClosed() {
        parentFrame.setLocationRelativeTo(null);
        if (previewMenuItem != null) {
            previewMenuItem.setSelected(false);
        }
    }

    private void handleAddObjectAction() {
        TopMetaDataPanel metaDataPanel = roomEditorPanel.getTopMetaDataPanel();
        String location = metaDataPanel.getLocationName();
        String area = metaDataPanel.getAreaName();
        String roomFile = metaDataPanel.getFileName();
        AddEditObjectDialog dialog = new AddEditObjectDialog(parentFrame, location, area, roomFile);
        dialog.setVisible(true);
    }
    private void handleEditObjectAction() {
        TopMetaDataPanel metaDataPanel = roomEditorPanel.getTopMetaDataPanel();
        String location = metaDataPanel.getLocationName();
        String area = metaDataPanel.getAreaName();
        String roomFile = metaDataPanel.getFileName();
        AddEditObjectDialog dialog = new AddEditObjectDialog(parentFrame, location, area, roomFile);
        dialog.setVisible(true);
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