package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.gui.panels.LongDescriptionPanel;
import com.phantomcoder.adventureeditor.gui.panels.MiddleDataPanel;
import com.phantomcoder.adventureeditor.gui.panels.TopMetaDataPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class RoomService implements IRoomService {

    private RoomData currentRoom;
    private Path savedRoomFilePath;
    private final TopMetaDataPanel topMetaDataPanel;
    private final MiddleDataPanel middleDataPanel;
    private final LongDescriptionPanel longDescriptionPanel;
    private final RoomPersistenceService persistenceService;

    public RoomService(TopMetaDataPanel topMetaDataPanel, MiddleDataPanel middleDataPanel, LongDescriptionPanel longDescriptionPanel) {
        this.topMetaDataPanel = topMetaDataPanel;
        this.middleDataPanel = middleDataPanel;
        this.longDescriptionPanel = longDescriptionPanel;
        this.persistenceService = new RoomPersistenceService();
    }

    @Override
    public RoomData getCurrentRoom() {
        return currentRoom;
    }

    @Override
    public Path getSavedRoomFilePath() {
        return savedRoomFilePath;
    }

    @Override
    public List<AmbianceEvent> getCurrentAmbianceEvents() {
        if (currentRoom == null || currentRoom.getAmbianceEvents() == null) {
            return Collections.emptyList();
        }
        return currentRoom.getAmbianceEvents();
    }

    @Override
    public void setCurrentAmbianceEvents(List<AmbianceEvent> events) {
        if (currentRoom != null) {
            currentRoom.setAmbianceEvents(events);
        }
    }

    @Override
    public void createAndSetCurrentRoom() {
        this.currentRoom = new RoomData();
        this.savedRoomFilePath = null;
    }

    @Override
    public void saveCurrentRoom() throws IOException {
        if (currentRoom == null) {
            throw new IllegalStateException("There is no current room to save.");
        }
        Path filePath = persistenceService.getRoomPath(
                currentRoom.getLocationName(),
                currentRoom.getAreaName(),
                currentRoom.getSubAreaName(),
                topMetaDataPanel.getFileName()
        );
        persistenceService.saveRoomData(currentRoom, filePath);
        this.savedRoomFilePath = filePath;
    }

    @Override
    public boolean loadRoomAndPopulateUI(Path filePath) throws IOException {
        boolean dataWasUpgraded = false;
        this.savedRoomFilePath = filePath;

        RoomData loadedRoom = persistenceService.loadRoomData(filePath);
        this.currentRoom = loadedRoom;

        if (this.currentRoom != null) {
            if (currentRoom.getAmbianceEvents() != null) {
                for (AmbianceEvent event : currentRoom.getAmbianceEvents()) {
                    if (event.getId() != null && !event.getId().startsWith("ambtxt_")) {
                        // CORRECTED: Pass all necessary fields to the ID generation method
                        String newFullId = AmbianceCreationService.generateFullId(
                                currentRoom.getLocationName(),
                                currentRoom.getAreaName(),
                                currentRoom.getSubAreaName(), // The missing argument
                                currentRoom.getRoomName(),
                                event.getText(),
                                currentRoom.getAmbianceEvents()
                        );
                        event.setId(newFullId);
                        dataWasUpgraded = true;
                    }
                }
            }
            populateUIFromCurrentRoom();
        }
        return dataWasUpgraded;
    }

    @Override
    public void gatherDataFromUI() {
        if (currentRoom == null) {
            return;
        }
        currentRoom.setLocationName(topMetaDataPanel.getLocationName());
        currentRoom.setAreaName(topMetaDataPanel.getAreaName());
        currentRoom.setSubAreaName(topMetaDataPanel.getSubAreaName());
        currentRoom.setRoomName(middleDataPanel.getRoomName());
        currentRoom.setShortDescription(middleDataPanel.getShortDescription());
        currentRoom.setLongDescription(longDescriptionPanel.getLongDescription());
        currentRoom.setTags(middleDataPanel.getRoomFlagsPanel().getSelectedTags());
    }

    @Override
    public void populateUIFromCurrentRoom() {
        if (currentRoom == null) {
            return;
        }
        topMetaDataPanel.setLocationName(currentRoom.getLocationName());
        topMetaDataPanel.setAreaName(currentRoom.getAreaName());
        topMetaDataPanel.setSubAreaName(currentRoom.getSubAreaName());

        String fileName = (savedRoomFilePath != null) ? savedRoomFilePath.getFileName().toString() : "";
        topMetaDataPanel.setFileName(fileName);

        middleDataPanel.setRoomName(currentRoom.getRoomName());
        middleDataPanel.setShortDescription(currentRoom.getShortDescription());
        middleDataPanel.getRoomFlagsPanel().setSelectedTags(currentRoom.getTags());
        longDescriptionPanel.setLongDescription(currentRoom.getLongDescription());
    }
}

