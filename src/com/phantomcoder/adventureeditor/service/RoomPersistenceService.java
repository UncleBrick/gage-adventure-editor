package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.config.AppConfig;
import com.google.gson.reflect.TypeToken;
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

    public RoomPersistenceService() {
        // Constructor is now empty as we are using static helper methods.
    }


    public RoomData loadRoomData(Path roomPath) throws IOException {
        if(AppConfig.isDebuggingEnabled()) {
            System.out.println("--- Debugging Ambiance Loading ---");
            System.out.println("1. Loading main room file: " + roomPath);
            RoomData roomData = JsonDataLoader.loadRoomFromJson(roomPath.toString());
            if (roomData == null) {
                roomData = new RoomData();
            }

            Path ambiancePath = getParallelPath(roomPath, "ambiance");
            System.out.println("2. Calculated parallel ambiance path: " + ambiancePath);
            System.out.println("3. Does ambiance file exist? " + Files.exists(ambiancePath));

            Type listType = new TypeToken<ArrayList<AmbianceEvent>>() {
            }.getType();
            List<AmbianceEvent> events = JsonDataLoader.loadDataFromJson(ambiancePath.toString(), listType);

            if (events != null) {
                System.out.println("4. Loaded " + events.size() + " ambiance events from file.");
                roomData.setAmbianceEvents(events);
            } else {
                System.out.println("4. No ambiance events loaded (file might be missing or empty).");
            }
            System.out.println("5. Final event count in RoomData object: " + roomData.getAmbianceEvents().size());
            System.out.println("------------------------------------");

            return roomData;

        } else {

            RoomData roomData = JsonDataLoader.loadRoomFromJson(roomPath.toString());

            if (roomData == null) {
                roomData = new RoomData();
            }

            Path ambiancePath = getParallelPath(roomPath, "ambiance");
            Type listType = new TypeToken<ArrayList<AmbianceEvent>>() {            }.getType();
            List<AmbianceEvent> events = JsonDataLoader.loadDataFromJson(ambiancePath.toString(), listType);
            roomData.setAmbianceEvents(events);

            return roomData;
        }
    }

    /**
     * Saves the complete room data to two separate files: one for the room, one for ambiance.
     * @param roomData The data object to save.
     * @param roomPath The path for the main room JSON file.
     * @throws IOException If a file error occurs.
     */
    public void saveRoomData(RoomData roomData, Path roomPath) throws IOException {
        // Calculate the parallel path for the ambiance file
        Path ambiancePath = getParallelPath(roomPath, "ambiance");

        // Save the list of ambiance events to its own file.
        JsonDataSaver.saveDataToJson(roomData.getAmbianceEvents(), ambiancePath.toString());

        // Temporarily nullify the ambiance list on the main object before saving it,
        // so the data isn't duplicated in the main room file.
        List<AmbianceEvent> originalEvents = roomData.getAmbianceEvents();
        roomData.setAmbianceEvents(null);

        JsonDataSaver.saveDataToJson(roomData, roomPath.toString());

        // Restore the list on the in-memory object after saving is complete.
        roomData.setAmbianceEvents(originalEvents);
    }

    /**
     * Helper method to calculate a parallel path (e.g., swapping 'rooms' for 'ambiance').
     * @param originalPath The original file path.
     * @param newSubfolder The name of the new subfolder (e.g., "ambiance").
     * @return The new, parallel path.
     */
    private Path getParallelPath(Path originalPath, String newSubfolder) {
        // Takes a path like .../rooms/location/area/file.json <---- this is wrong it is supposed to be ----> /location/area/rooms/file.json
        // and converts it to .../ambiance/location/area/file.json  <---- this is wrong it is supposed to be ----> /location/area/ambiance/file.json
        Path parent = originalPath.getParent(); // .../rooms/location/area
        Path grandParent = parent.getParent(); // .../rooms/location
        Path greatGrandParent = grandParent.getParent(); // .../rooms

        return greatGrandParent.getParent() // Should now return: /location/area/ambiance/file.json
                .resolve(greatGrandParent.getFileName()) // location e.g. greymere
                .resolve(grandParent.getFileName()) // area e.g. the great city of mur
                .resolve(newSubfolder) // ambiance folder
                .resolve(originalPath.getFileName()); // filename e.g. 00_00_00.json

    }

    public Path getRoomPath(String locationName, String areaName, String fileName) {
        Path projectRoot = PathUtil.getAppBaseDirectory();
        String safeLocationName = PathUtil.toSafeFileName(locationName);
        String safeAreaName = PathUtil.toSafeFileName(areaName);

        return projectRoot
                .resolve("resources")
                .resolve("data")
                .resolve(safeLocationName)
                .resolve(safeAreaName)
                .resolve("rooms")
                .resolve(fileName);
    }
}