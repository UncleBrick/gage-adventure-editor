package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IRoomService {
    RoomData getCurrentRoom();
    String getSavedRoomFilePath();
    List<AmbianceEvent> getCurrentAmbianceEvents();
    void setCurrentAmbianceEvents(List<AmbianceEvent> events);
    void createAndSetCurrentRoom() throws IllegalArgumentException;
    void saveCurrentRoom() throws IllegalStateException, IllegalArgumentException, IOException;
    void loadRoomAndPopulateUI(Path fullFilePath) throws IllegalArgumentException, IOException;
}