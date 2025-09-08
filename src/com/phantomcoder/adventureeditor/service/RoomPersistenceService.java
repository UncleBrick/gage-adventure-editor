package com.phantomcoder.adventureeditor.service;

import com.google.gson.reflect.TypeToken;
import com.phantomcoder.adventureeditor.constants.DataConstants;
import com.phantomcoder.adventureeditor.model.*;
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

    public void saveRoomData(RoomData roomData, Path filePath) throws IOException {
        Path resourcesDir = PathUtil.getAppBaseDirectory().resolve(DataConstants.RESOURCES_DIRECTORY_NAME);
        String relativeRoomPath = resourcesDir.relativize(filePath).toString();

        String roomFileType = DataConstants.DATA_FILE_TYPES.get("ROOMS");
        JsonDataSaver.saveDataToJson(roomData, relativeRoomPath, roomFileType);

        saveAllParallelData(roomData, filePath, resourcesDir);
    }

    public RoomData loadRoomData(Path filePath) throws IOException {
        RoomData roomData = JsonDataLoader.loadDataFromJson(filePath.toString(), RoomData.class);

        if (roomData == null) {
            return null;
        }

        loadAllParallelData(roomData, filePath);

        return roomData;
    }

    private void saveAllParallelData(RoomData roomData, Path roomFilePath, Path resourcesDir) throws IOException {
        for (String key : DataConstants.DATA_DIRECTORIES.keySet()) {
            if (key.equals("ROOMS")) {
                continue;
            }

            List<?> dataList = getListFromRoomData(roomData, key);

            if (dataList != null && !dataList.isEmpty()) {
                Path parallelPath = PathUtil.getParallelPath(roomFilePath, "ROOMS", key);
                String relativeParallelPath = resourcesDir.relativize(parallelPath).toString();
                String fileType = DataConstants.DATA_FILE_TYPES.get(key);
                JsonDataSaver.saveDataToJson(dataList, relativeParallelPath, fileType);
            }
        }
    }

    private void loadAllParallelData(RoomData roomData, Path roomFilePath) throws IOException {
        for (String key : DataConstants.DATA_DIRECTORIES.keySet()) {
            if (key.equals("ROOMS")) {
                continue;
            }

            Path parallelPath = PathUtil.getParallelPath(roomFilePath, "ROOMS", key);

            if (Files.exists(parallelPath)) {
                Type listType = getListTypeToken(key);
                if (listType == null) continue;

                List<?> loadedList = JsonDataLoader.loadDataFromJson(parallelPath.toString(), listType);
                if (loadedList != null) {
                    setListInRoomData(roomData, key, loadedList);
                }
            }
        }
    }

    private List<?> getListFromRoomData(RoomData roomData, String key) {
        return switch (key) {
            case "AMBIANCES" -> roomData.getAmbianceEvents();
            case "OBJECTS" -> roomData.getObjects();
            case "NPCS" -> roomData.getNpcs();
            case "COMMANDS" -> roomData.getCommands();
            case "EXITS" -> roomData.getExits();
            case "MAGIC" -> roomData.getMagic();
            case "QUESTS" -> roomData.getQuests();
            default -> null;
        };
    }

    private void setListInRoomData(RoomData roomData, String key, List<?> list) {
        switch (key) {
            case "AMBIANCES" -> roomData.setAmbianceEvents((List<AmbianceEvent>) list);
            case "OBJECTS" -> roomData.setObjects((List<ObjectData>) list);
            case "NPCS" -> roomData.setNpcs((List<NpcData>) list);
            case "COMMANDS" -> roomData.setCommands((List<CommandData>) list);
            case "EXITS" -> roomData.setExits((List<ExitData>) list);
            case "MAGIC" -> roomData.setMagic((List<MagicData>) list);
            case "QUESTS" -> roomData.setQuests((List<QuestData>) list);
        }
    }

    private Type getListTypeToken(String key) {
        return switch (key) {
            case "AMBIANCES" -> new TypeToken<ArrayList<AmbianceEvent>>() {}.getType();
            case "OBJECTS" -> new TypeToken<ArrayList<ObjectData>>() {}.getType();
            case "NPCS" -> new TypeToken<ArrayList<NpcData>>() {}.getType();
            case "COMMANDS" -> new TypeToken<ArrayList<CommandData>>() {}.getType();
            case "EXITS" -> new TypeToken<ArrayList<ExitData>>() {}.getType();
            case "MAGIC" -> new TypeToken<ArrayList<MagicData>>() {}.getType();
            case "QUESTS" -> new TypeToken<ArrayList<QuestData>>() {}.getType();
            default -> null;
        };
    }

    public Path getRoomPath(String location, String area, String fileName) {
        String safeLocation = PathUtil.toSafeFileName(location);
        String safeArea = PathUtil.toSafeFileName(area);
        String nameWithoutExtension = fileName.endsWith(".json")
                ? fileName.substring(0, fileName.length() - ".json".length())
                : fileName;
        String safeFileName = PathUtil.toSafeFileName(nameWithoutExtension);
        String jsonFileName = safeFileName + ".json";

        return PathUtil.getAppBaseDirectory()
                .resolve(DataConstants.BASE_DATA_PATH)
                .resolve(safeLocation)
                .resolve(safeArea)
                .resolve(DataConstants.ROOMS_DIRECTORY_NAME)
                .resolve(jsonFileName);
    }
}