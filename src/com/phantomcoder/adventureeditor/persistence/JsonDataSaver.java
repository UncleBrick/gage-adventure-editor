package com.phantomcoder.adventureeditor.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phantomcoder.adventureeditor.constants.DataConstants;
import com.phantomcoder.adventureeditor.util.PathUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonDataSaver {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private JsonDataSaver() {}

    /**
     * Saves any given data object to a JSON file using the FileDataWrapper format.
     * @param dataToSave The object to serialize to JSON.
     * @param partialPath The path to the file relative to the resources/ directory.
     * @param fileType A string describing the data type (e.g., "Room Data").
     * @throws IOException If an I/O error occurs.
     */
    public static void saveDataToJson(Object dataToSave, String partialPath, String fileType) throws IOException {
        Path fullPath = PathUtil.getAppBaseDirectory().resolve(DataConstants.RESOURCES_DIRECTORY_NAME).resolve(partialPath);

        // Create the wrapper object with the data and metadata.
        FileDataWrapper<Object> wrapper = new FileDataWrapper<>(fileType, DataConstants.FILE_DATA_WRAPPER_VERSION, dataToSave);

        Path parentDir = fullPath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        // Serialize the entire wrapper object to the file.
        Files.writeString(fullPath, GSON.toJson(wrapper));
    }
}