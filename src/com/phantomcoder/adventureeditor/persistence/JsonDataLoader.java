package com.phantomcoder.adventureeditor.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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

    /**
     * Loads a JSON file that uses the FileDataWrapper format, unwraps it, and returns the core data.
     * @param filePath The absolute path to the JSON file.
     * @param typeOfT  The specific Type of the data to be loaded (e.g., RoomData.class or a TypeToken for a List).
     * @param <T>      The generic type of the data.
     * @return The deserialized data object of type T, or null if the file doesn't exist.
     * @throws IOException If a file reading error occurs.
     */
    public static <T> T loadDataFromJson(String filePath, Type typeOfT) throws IOException {
        // Create the specific generic type for FileDataWrapper<T>
        Type wrapperType = TypeToken.getParameterized(FileDataWrapper.class, typeOfT).getType();

        // Load the wrapper object from the JSON file.
        FileDataWrapper<T> wrapper = loadDataWrapper(filePath, wrapperType);

        if (wrapper == null) {
            return null;
        }

        // Extract the core data from the wrapper.
        T result = wrapper.getData();

        // Preserve backward-compatibility logic for older AmbianceEvent formats.
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

    /**
     * A private helper that reads a JSON file and deserializes it into a FileDataWrapper of a given type.
     */
    private static <T> FileDataWrapper<T> loadDataWrapper(String filePath, Type wrapperTypeOfT) throws IOException {
        Path fullPath = Paths.get(filePath);

        if (!Files.exists(fullPath)) {
            return null;
        }

        String jsonContent = Files.readString(fullPath);
        return GSON.fromJson(jsonContent, wrapperTypeOfT);
    }
}