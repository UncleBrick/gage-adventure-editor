package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.gui.panels.LongDescriptionPanel;
import com.phantomcoder.adventureeditor.gui.panels.MiddleDataPanel;
import com.phantomcoder.adventureeditor.gui.panels.RoomEditorPanel;
import com.phantomcoder.adventureeditor.gui.panels.TopMetaDataPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class RoomService implements IRoomService {

    private RoomData currentRoom;
    private Path savedRoomFilePath;
    private final TopMetaDataPanel topMetaDataPanel;
    private final MiddleDataPanel middleDataPanel;
    private final LongDescriptionPanel longDescriptionPanel;
    private final RoomPersistenceService persistenceService;

    public RoomService(RoomEditorPanel roomEditorPanel, MiddleDataPanel middleDataPanel,
                       LongDescriptionPanel longDescriptionPanel, TopMetaDataPanel topMetaDataPanel) {
        this.topMetaDataPanel = topMetaDataPanel;
        this.middleDataPanel = middleDataPanel;
        this.longDescriptionPanel = longDescriptionPanel;
        this.persistenceService = new RoomPersistenceService();
        createAndSetCurrentRoom();
    }

    @Override
    public void loadRoomAndPopulateUI(Path filePath) throws IOException {
        RoomData roomData = persistenceService.loadRoomData(filePath);

        if (roomData.getAmbianceEvents() != null) {
            for (AmbianceEvent event : roomData.getAmbianceEvents()) {
                if (event.getGuid() == null || event.getGuid().isEmpty()) {
                    event.setGuid(UUID.randomUUID().toString());
                }
            }
        }

        this.currentRoom = roomData;
        this.savedRoomFilePath = filePath;

        topMetaDataPanel.setLocationName(currentRoom.getLocationName());
        topMetaDataPanel.setAreaName(currentRoom.getAreaName());
        topMetaDataPanel.setFileName(filePath.getFileName().toString());
        middleDataPanel.setRoomName(currentRoom.getRoomName());
        middleDataPanel.setShortDescription(currentRoom.getShortDescription());
        middleDataPanel.getRoomFlagsPanel().setSelectedTags(currentRoom.getTags());
        longDescriptionPanel.setLongDescription(currentRoom.getLongDescription());
    }

    @Override
    public void saveCurrentRoom() throws IOException {
        currentRoom.setLocationName(topMetaDataPanel.getLocationName());
        currentRoom.setAreaName(topMetaDataPanel.getAreaName());
        currentRoom.setRoomName(middleDataPanel.getRoomName());
        currentRoom.setShortDescription(middleDataPanel.getShortDescription());
        currentRoom.setTags(middleDataPanel.getRoomFlagsPanel().getSelectedTags());
        currentRoom.setLongDescription(longDescriptionPanel.getLongDescription());

        Path filePath = persistenceService.getRoomPath(
                topMetaDataPanel.getLocationName(),
                topMetaDataPanel.getAreaName(),
                topMetaDataPanel.getFileName()
        );

        persistenceService.saveRoomData(currentRoom, filePath);
        this.savedRoomFilePath = filePath;
    }

    @Override
    public void createAndSetCurrentRoom() {
        this.currentRoom = new RoomData();
        this.savedRoomFilePath = null;
    }

    @Override
    public RoomData getCurrentRoom() {
        return currentRoom;
    }

    /**
     * FIX: Added the missing override for getSavedRoomFilePath.
     */
    @Override
    public Path getSavedRoomFilePath() {
        return savedRoomFilePath;
    }

    @Override
    public List<AmbianceEvent> getCurrentAmbianceEvents() {
        return currentRoom.getAmbianceEvents();
    }

    @Override
    public void setCurrentAmbianceEvents(List<AmbianceEvent> events) {
        currentRoom.setAmbianceEvents(events);
    }
}