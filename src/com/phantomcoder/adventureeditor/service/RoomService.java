package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.gui.panels.LongDescriptionPanel;
import com.phantomcoder.adventureeditor.gui.panels.MiddleDataPanel;
import com.phantomcoder.adventureeditor.gui.panels.TopMetaDataPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.persistence.JsonDataSaver;
import com.phantomcoder.adventureeditor.persistence.RoomPersistenceService;
import com.phantomcoder.adventureeditor.util.PathUtil;
import com.phantomcoder.adventureeditor.validation.RoomValidator;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RoomService implements IRoomService {

    private final MiddleDataPanel middleDataPanel;
    private final LongDescriptionPanel longDescriptionPanel;
    private final TopMetaDataPanel topMetaDataPanel;
    private RoomData currentRoom;
    private List<AmbianceEvent> currentAmbianceEvents;
    private final RoomCreationService roomCreationService;
    private final RoomPersistenceService roomPersistenceService;
    private String lastSavedOrLoadedFilePath;

    public RoomService(MiddleDataPanel middleDataPanel, LongDescriptionPanel longDescriptionPanel, TopMetaDataPanel topMetaDataPanel) {
        this.middleDataPanel = Objects.requireNonNull(middleDataPanel, "MiddleDataPanel cannot be null");
        this.longDescriptionPanel = Objects.requireNonNull(longDescriptionPanel, "LongDescriptionPanel cannot be null");
        this.topMetaDataPanel = Objects.requireNonNull(topMetaDataPanel, "TopMetaDataPanel cannot be null");
        RoomValidator roomValidator = new RoomValidator();
        this.roomCreationService = new RoomCreationService(middleDataPanel, longDescriptionPanel, topMetaDataPanel, roomValidator);
        this.roomPersistenceService = new RoomPersistenceService();
        this.lastSavedOrLoadedFilePath = "N/A";
        this.currentAmbianceEvents = new ArrayList<>();
    }

    @Override
    public RoomData getCurrentRoom() {
        return currentRoom;
    }

    @Override
    public String getSavedRoomFilePath() {
        return lastSavedOrLoadedFilePath;
    }

    @Override
    public List<AmbianceEvent> getCurrentAmbianceEvents() {
        return currentAmbianceEvents;
    }

    @Override
    public void setCurrentAmbianceEvents(List<AmbianceEvent> events) {
        this.currentAmbianceEvents = events;
    }

    @Override
    public void createAndSetCurrentRoom() throws IllegalArgumentException {
        RoomData newRoom = roomCreationService.createRoomData();
        // The getSelectedTags call is now in RoomCreationService, but if you needed it here,
        // you would use: middleDataPanel.getRoomFlagsPanel().getSelectedTags();
        // This line below is now redundant as it's handled by RoomCreationService
        // newRoom.setRoomTags(middleDataPanel.getRoomFlagsPanel().getSelectedTags());
        this.currentRoom = newRoom;
    }

    @Override
    public void saveCurrentRoom() throws IllegalStateException, IllegalArgumentException, IOException {
        createAndSetCurrentRoom();
        if (currentRoom == null) {
            throw new IllegalStateException("No room data has been prepared to save.");
        }
        String locationName = topMetaDataPanel.getLocationName();
        String areaName = topMetaDataPanel.getAreaName();
        String fileName = topMetaDataPanel.getFileName();

        String roomDataPath = Paths.get("data", PathUtil.toOsSafeLowerCase(locationName), PathUtil.toOsSafeLowerCase(areaName), "rooms", PathUtil.toOsSafeLowerCase(fileName) + ".json").toString();
        JsonDataSaver.saveDataToJson(currentRoom, roomDataPath);

        if (currentAmbianceEvents != null && !currentAmbianceEvents.isEmpty()) {
            String ambianceDataPath = Paths.get("data", PathUtil.toOsSafeLowerCase(locationName), PathUtil.toOsSafeLowerCase(areaName), "ambiance", PathUtil.toOsSafeLowerCase(fileName) + ".json").toString();
            JsonDataSaver.saveDataToJson(currentAmbianceEvents, ambianceDataPath);
        }

        this.lastSavedOrLoadedFilePath = PathUtil.getAppBaseDirectory().resolve("resources").resolve(roomDataPath).toAbsolutePath().toString();
    }

    @Override
    public void loadRoomAndPopulateUI(Path fullFilePath) throws IllegalArgumentException, IOException {
        Objects.requireNonNull(fullFilePath, "File path for loading cannot be null.");
        RoomData loadedRoom = roomPersistenceService.loadRoom(fullFilePath.toString());
        if (loadedRoom != null) {
            this.currentRoom = loadedRoom;

            String ambiancePath = fullFilePath.toString().replace(java.io.File.separator + "rooms" + java.io.File.separator, java.io.File.separator + "ambiance" + java.io.File.separator);
            this.currentAmbianceEvents = roomPersistenceService.loadAmbiance(ambiancePath);

            topMetaDataPanel.setLocationName(loadedRoom.getLocationName());
            topMetaDataPanel.setAreaName(loadedRoom.getAreaName());
            middleDataPanel.setRoomName(loadedRoom.getRoomName());
            middleDataPanel.setShortDescription(loadedRoom.getShortDescription());
            // Set the tags on the new dedicated flags panel
            middleDataPanel.getRoomFlagsPanel().setSelectedTags(loadedRoom.getRoomTags());
            longDescriptionPanel.setLongDescription(loadedRoom.getLongDescription());
            String actualFileName = fullFilePath.getFileName().toString().replace(".json", "");
            topMetaDataPanel.setFileName(actualFileName);
            this.lastSavedOrLoadedFilePath = fullFilePath.toAbsolutePath().toString();
        } else {
            throw new IOException("Room file not found or could not be loaded from: " + fullFilePath.toString());
        }
    }
}