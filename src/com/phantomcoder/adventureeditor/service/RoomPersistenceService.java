package com.phantomcoder.adventureeditor.service;

import com.google.gson.reflect.TypeToken;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.persistence.JsonDataLoader;
import com.phantomcoder.adventureeditor.persistence.JsonDataSaver;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomPersistenceService {

    public void saveRoom(RoomData room, String relativeFilePath) throws IOException {
        Objects.requireNonNull(room, "RoomData to save cannot be null.");
        Objects.requireNonNull(relativeFilePath, "File path cannot be null.");
        JsonDataSaver.saveDataToJson(room, relativeFilePath);
    }

    public RoomData loadRoom(String relativeFilePath) throws IOException {
        Objects.requireNonNull(relativeFilePath, "File path cannot be null.");
        return JsonDataLoader.loadRoomFromJson(relativeFilePath);
    }

    // --- NEW METHOD FOR LOADING AMBIANCE ---
    public List<AmbianceEvent> loadAmbiance(String relativeFilePath) throws IOException {
        Objects.requireNonNull(relativeFilePath, "File path cannot be null.");

        // Define the specific type we expect from the JSON (a list of AmbianceEvent)
        Type listType = new TypeToken<ArrayList<AmbianceEvent>>(){}.getType();
        List<AmbianceEvent> events = JsonDataLoader.loadDataFromJson(relativeFilePath, listType);

        // If the file didn't exist, the loader returned null.
        // In that case, we return a new empty list.
        if (events == null) {
            return new ArrayList<>();
        }

        return events;
    }
}