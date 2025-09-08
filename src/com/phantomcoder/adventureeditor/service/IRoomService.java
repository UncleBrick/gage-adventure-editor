package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IRoomService {
    RoomData getCurrentRoom();
    Path getSavedRoomFilePath();
    List<AmbianceEvent> getCurrentAmbianceEvents();
    void setCurrentAmbianceEvents(List<AmbianceEvent> events);
    void createAndSetCurrentRoom();
    void saveCurrentRoom() throws IOException;
    void loadRoomAndPopulateUI(Path filePath) throws IOException;
    void gatherDataFromUI();
    void populateUIFromCurrentRoom();
}