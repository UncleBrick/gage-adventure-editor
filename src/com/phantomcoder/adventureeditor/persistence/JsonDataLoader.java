package com.phantomcoder.adventureeditor.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class JsonDataLoader {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private JsonDataLoader() {}

    public static <T> T loadDataFromJson(String filePath, Type typeOfT) throws IOException {
        Path fullPath = Paths.get(filePath);

        if (!Files.exists(fullPath)) {
            return null;
        }

        String jsonContent = Files.readString(fullPath);
        T result = GSON.fromJson(jsonContent, typeOfT);

        // FIX: Add backward-compatibility logic for AmbianceEvent GUIDs.
        if (result instanceof RoomData) {
            RoomData roomData = (RoomData) result;
            if (roomData.getAmbianceEvents() != null) {
                for (AmbianceEvent event : roomData.getAmbianceEvents()) {
                    if (event.getGuid() == null || event.getGuid().isEmpty()) {
                        event.setGuid(UUID.randomUUID().toString());
                    }
                }
            }
        }

        return result;
    }

    public static RoomData loadRoomFromJson(String filePath) throws IOException {
        return loadDataFromJson(filePath, RoomData.class);
    }
}