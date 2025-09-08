package com.phantomcoder.adventureeditor.service;

import com.google.gson.reflect.TypeToken;
import com.phantomcoder.adventureeditor.constants.DataConstants;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.persistence.JsonDataLoader;
import com.phantomcoder.adventureeditor.persistence.JsonDataSaver;
import com.phantomcoder.adventureeditor.util.PathUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RoomPersistenceService {

    /**
     * Saves the core room data and its parallel ambiance data to separate JSON files.
     *
     * @param roomData The RoomData object to save.
     * @param filePath The absolute path to the target room file.
     * @throws IOException If an I/O error occurs.
     */
    public void saveRoomData(RoomData roomData, Path filePath) throws IOException {
        // The JsonDataSaver expects a path relative to the 'resources' directory.
        Path resourcesDir = PathUtil.getAppBaseDirectory().resolve(DataConstants.RESOURCES_DIRECTORY_NAME);
        String relativeRoomPath = resourcesDir.relativize(filePath).toString();

        // 1. Save the main room file.
        JsonDataSaver.saveDataToJson(roomData, relativeRoomPath);

        // 2. Check if there is ambiance data to save.
        if (roomData.getAmbianceEvents() != null && !roomData.getAmbianceEvents().isEmpty()) {
            // 3. Get the parallel path using the new dynamic utility.
            Path ambiancePath = PathUtil.getParallelPath(filePath, "ROOMS", "AMBIANCES");
            String relativeAmbiancePath = resourcesDir.relativize(ambiancePath).toString();
            JsonDataSaver.saveDataToJson(roomData.getAmbianceEvents(), relativeAmbiancePath);
        }
    }

    /**
     * Loads the core room data and attempts to load its parallel ambiance data.
     *
     * @param filePath The absolute path to the room file to load.
     * @return A populated RoomData object, or null if the room file doesn't exist.
     * @throws IOException If a file reading error occurs.
     */
    public RoomData loadRoomData(Path filePath) throws IOException {
        // 1. Load the main room data.
        RoomData roomData = JsonDataLoader.loadRoomFromJson(filePath.toString());

        // If the room file doesn't exist, exit early.
        if (roomData == null) {
            return null;
        }

        // 2. Attempt to load the parallel ambiance data.
        Path ambiancePath = PathUtil.getParallelPath(filePath, "ROOMS", "AMBIANCES");
        if (Files.exists(ambiancePath)) {
            Type listType = new TypeToken<ArrayList<AmbianceEvent>>() {}.getType();
            List<AmbianceEvent> events = JsonDataLoader.loadDataFromJson(ambiancePath.toString(), listType);

            if (events != null) {
                roomData.setAmbianceEvents(events);
            }
        }

        return roomData;
    }

    /**
     * Constructs the full, absolute path for a room file based on its metadata.
     *
     * @param location The location name (e.g., Dungeon of Despair).
     * @param area     The area name (e.g., Level 1).
     * @param fileName The simple file name (e.g., throne_room).
     * @return The absolute Path object for the room file.
     */
    public Path getRoomPath(String location, String area, String fileName) {
        String safeLocation = PathUtil.toSafeFileName(location);
        String safeArea = PathUtil.toSafeFileName(area);
        String safeFileName = PathUtil.toSafeFileName(fileName);
        String jsonFileName = safeFileName.endsWith(".json") ? safeFileName : safeFileName + ".json";

        // CORRECTED PATH ORDER: data/[location]/[area]/[datatype]/
        return PathUtil.getAppBaseDirectory()
                .resolve(DataConstants.BASE_DATA_PATH)
                .resolve(safeLocation)
                .resolve(safeArea)
                .resolve(DataConstants.ROOMS_DIRECTORY_NAME)
                .resolve(jsonFileName);
    }
}