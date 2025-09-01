package com.phantomcoder.adventureeditor.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonDataLoader {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private JsonDataLoader() {}

    // --- NEW GENERIC LOAD METHOD ---
    public static <T> T loadDataFromJson(String filePath, Type typeOfT) throws IOException {
        Path fullPath = Paths.get(filePath);

        // Gracefully handle missing files by returning null
        if (!Files.exists(fullPath)) {
            return null;
        }

        String jsonContent = Files.readString(fullPath);
        return GSON.fromJson(jsonContent, typeOfT);
    }

    public static RoomData loadRoomFromJson(String filePath) throws IOException {
        // Now uses the generic method
        return loadDataFromJson(filePath, RoomData.class);
    }
}