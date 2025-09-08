package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.config.AppConfig;
import com.phantomcoder.adventureeditor.gui.panels.LongDescriptionPanel;
import com.phantomcoder.adventureeditor.gui.panels.MiddleDataPanel;
import com.phantomcoder.adventureeditor.gui.panels.TopMetaDataPanel;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
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
                topMetaDataPanel.getFileName()
        );
        persistenceService.saveRoomData(currentRoom, filePath);
        this.savedRoomFilePath = filePath;
    }

    @Override
    public void loadRoomAndPopulateUI(Path filePath) throws IOException {
        // Corrected logic: assign the path before trying to load the data.
        this.savedRoomFilePath = filePath;

        // Attempt to load the room data.
        RoomData loadedRoom = persistenceService.loadRoomData(filePath);

        // Explicitly set the currentRoom variable.
        this.currentRoom = loadedRoom;

        // Populate the UI with the data from the currentRoom variable.
        if (this.currentRoom != null) {
            populateUIFromCurrentRoom();
        }
    }

    @Override
    public void gatherDataFromUI() {
        if (currentRoom == null) {
            return;
        }
        currentRoom.setLocationName(topMetaDataPanel.getLocationName());
        currentRoom.setAreaName(topMetaDataPanel.getAreaName());
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

        // Debugging print statements to check the values of each field
        System.out.println("--- Values in RoomData object before UI population ---");
        System.out.println("Location Name: " + currentRoom.getLocationName());
        System.out.println("Area Name: " + currentRoom.getAreaName());
        System.out.println("Room Name: " + currentRoom.getRoomName());
        System.out.println("Short Description: " + currentRoom.getShortDescription());
        System.out.println("Long Description: " + currentRoom.getLongDescription());
        System.out.println("Tags: " + currentRoom.getTags());
        System.out.println("------------------------------------");

        // Set values for TopMetaDataPanel
        System.out.println("Setting Location Name in UI: " + currentRoom.getLocationName());
        topMetaDataPanel.setLocationName(currentRoom.getLocationName());

        System.out.println("Setting Area Name in UI: " + currentRoom.getAreaName());
        topMetaDataPanel.setAreaName(currentRoom.getAreaName());

        String fileName = (savedRoomFilePath != null) ? savedRoomFilePath.getFileName().toString() : "";
        System.out.println("Setting File Name in UI: " + fileName);
        topMetaDataPanel.setFileName(fileName);

        // Set values for MiddleDataPanel
        System.out.println("Setting Room Name in UI: " + currentRoom.getRoomName());
        middleDataPanel.setRoomName(currentRoom.getRoomName());

        System.out.println("Setting Short Description in UI: " + currentRoom.getShortDescription());
        middleDataPanel.setShortDescription(currentRoom.getShortDescription());

        System.out.println("Setting Tags in UI: " + currentRoom.getTags());
        middleDataPanel.getRoomFlagsPanel().setSelectedTags(currentRoom.getTags());

        // Set value for LongDescriptionPanel
        System.out.println("Setting Long Description in UI: " + currentRoom.getLongDescription());
        longDescriptionPanel.setLongDescription(currentRoom.getLongDescription());
    }
}