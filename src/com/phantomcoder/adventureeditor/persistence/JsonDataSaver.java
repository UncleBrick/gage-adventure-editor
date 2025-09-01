package com.phantomcoder.adventureeditor.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.util.PathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonDataSaver {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private JsonDataSaver() {}

    /**
     * Saves any given data object to a JSON file inside the resources directory.
     * @param dataToSave The object to serialize to JSON.
     * @param partialPath The path to the file relative to the resources/ directory.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveDataToJson(Object dataToSave, String partialPath) throws IOException {
        Path fullPath = PathUtil.getAppBaseDirectory().resolve("resources").resolve(partialPath);

        Path parentDir = fullPath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        Files.writeString(fullPath, GSON.toJson(dataToSave));
    }

    /**
     * Saves a list of RoomData objects to a JSON file.
     * @deprecated This method is specific; prefer using the generic saveDataToJson.
     */
    @Deprecated
    public static void saveRoomsToJson(List<RoomData> rooms, String partialPath) throws IOException {
        saveDataToJson(rooms, partialPath);
    }

    /**
     * Saves a single RoomData object to a JSON file.
     * @deprecated This method is specific; prefer using the generic saveDataToJson.
     */
    @Deprecated
    public static void saveRoomToJson(RoomData room, String partialPath) throws IOException {
        saveDataToJson(room, partialPath);
    }
}