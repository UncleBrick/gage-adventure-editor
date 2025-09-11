package com.phantomcoder.adventureeditor.controller;

import com.phantomcoder.adventureeditor.config.AppConfig;
import com.phantomcoder.adventureeditor.constants.DialogConstants;
import com.phantomcoder.adventureeditor.gui.MainApplicationFrame;
import com.phantomcoder.adventureeditor.gui.dialogs.AmbianceManagerDialog;
import com.phantomcoder.adventureeditor.gui.dialogs.SaveWarningDialog;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JCheckBoxMenuItem;

public class RoomController {

    private final RoomEditorPanel roomEditorPanel;
    private final MainApplicationFrame parentFrame;
    private final ActionManager actionManager;
    private final IRoomService roomService;
    private final DirtyStateManager dirtyStateManager;
    private final AmbianceManagerDialog ambianceManagerDialog;
    private ObjectController objectController;
    private JCheckBoxMenuItem previewMenuItem;
    private boolean isRoomDirty = false;
    private boolean isPopulatingForm = false;

    public RoomController(RoomEditorPanel roomEditorPanel, MainApplicationFrame parentFrame, IRoomService roomService) {
        this.roomEditorPanel = roomEditorPanel;
        this.parentFrame = parentFrame;
        this.actionManager = new ActionManager(this, parentFrame);
        this.roomService = roomService;
        this.dirtyStateManager = new DirtyStateManager();
        this.ambianceManagerDialog = new AmbianceManagerDialog(parentFrame, this);

        attachChangeListeners();
        wireUpButtons();
        handleAddRoomAction();
    }

    /**
     * Provides access to the room service for dependent components.
     * @return The instance of the room service.
     */
    public IRoomService getRoomService() {
        return roomService;
    }

    public void setObjectController(ObjectController objectController) {
        this.objectController = objectController;
    }

    private void attachChangeListeners() {
        DirtyStateListener.applyTo(this.roomEditorPanel, this::onFormChanged);
    }

    private void wireUpButtons() {
        roomEditorPanel.getMiddleDataPanel().getManageAmbianceButton().addActionListener(e -> handleManageAmbianceAction());
    }

    private void onFormChanged() {
        if (isPopulatingForm) {
            return;
        }
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
        boolean isRoomActive = (roomService.getSavedRoomFilePath() != null);

        actionManager.saveAction.setEnabled(isRoomDirty && isMetadataValid());
        actionManager.saveAsAction.setEnabled(isRoomActive);
        actionManager.closeAction.setEnabled(isRoomActive);

        roomEditorPanel.getMiddleDataPanel().getManageAmbianceButton().setEnabled(isRoomActive);
        roomEditorPanel.getMiddleDataPanel().getManageTimeStatesButton().setEnabled(isRoomActive);
    }

    public void handleAddRoomAction() {
        if (isRoomDirty) {
            // TODO: Implement "Warn on close" logic
        }
        roomService.createAndSetCurrentRoom();

        isPopulatingForm = true;
        roomService.populateUIFromCurrentRoom();
        isPopulatingForm = false;

        dirtyStateManager.takeSnapshot(roomService.getCurrentRoom());
        setRoomDirty(false);
        updateActionStates();
        parentFrame.setStatus("New room created. Fill in the details to save.");

        if (objectController != null) {
            objectController.onNewRoom();
        }
    }

    public void handleLoadRoomAction() {
        if (isRoomDirty) {
            // TODO: Implement "Warn on close" logic
        }

        Optional<Path> result = RoomFileChooser.showLoadRoomDialog(parentFrame);

        if (result.isPresent()) {
            Path selectedPath = result.get();
            try {
                isPopulatingForm = true;
                boolean dataWasUpgraded = roomService.loadRoomAndPopulateUI(selectedPath);
                isPopulatingForm = false;

                RoomData currentRoom = roomService.getCurrentRoom();
                if (currentRoom != null) {
                    dirtyStateManager.takeSnapshot(currentRoom);
                    setRoomDirty(false);

                    if (dataWasUpgraded) {
                        setRoomDirty(true);
                        parentFrame.setStatus("Loaded and upgraded room: " + selectedPath.getFileName() + ". Please save.");
                    } else {
                        parentFrame.setStatus("Successfully loaded room: " + selectedPath.getFileName());
                    }

                    if (objectController != null) {
                        objectController.onRoomLoaded(currentRoom);
                    }
                } else {
                    UiHelper.showErrorDialog(parentFrame, "Load Error", "Failed to load room from file. The file may be empty or malformed.");
                }
            } catch (IOException ex) {
                UiHelper.showErrorDialog(parentFrame, "Load Error", "Failed to load room from file: " + ex.getMessage());
            }
        }
    }

    public void handleSaveCurrentRoomAction() {
        List<String> emptyFields = checkForEmptyOptionalFields();

        if (!emptyFields.isEmpty() && AppConfig.shouldShowSaveWarning()) {
            SaveWarningDialog dialog = new SaveWarningDialog(parentFrame, emptyFields);
            int choice = dialog.showDialog();

            if (dialog.isDontShowAgainSelected()) {
                AppConfig.setShouldShowSaveWarning(false);
                AppConfig.saveConfig();
            }

            if (choice == DialogConstants.CANCEL_OPTION) {
                return;
            }
        }

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

    private List<String> checkForEmptyOptionalFields() {
        List<String> emptyFields = new ArrayList<>();
        if (roomEditorPanel.getMiddleDataPanel().getRoomName().trim().isEmpty()) {
            emptyFields.add("Room Name");
        }
        if (roomEditorPanel.getMiddleDataPanel().getShortDescription().trim().isEmpty()) {
            emptyFields.add("Short Description");
        }
        if (roomEditorPanel.getLongDescriptionPanel().getLongDescription().trim().isEmpty()) {
            emptyFields.add("Long Description");
        }
        return emptyFields;
    }

    public void handleManageAmbianceAction() {
        ambianceManagerDialog.displayEvents(roomService.getCurrentAmbianceEvents());
        ambianceManagerDialog.setVisible(true);
    }

    public void updateAmbianceData(List<AmbianceEvent> events) {
        roomService.setCurrentAmbianceEvents(events);
        boolean dirty = dirtyStateManager.isDirty(roomService.getCurrentRoom());
        setRoomDirty(dirty);
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public void setPreviewMenuItem(JCheckBoxMenuItem previewMenuItem) {
        this.previewMenuItem = previewMenuItem;
    }
}