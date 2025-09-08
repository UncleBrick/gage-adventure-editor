package com.phantomcoder.adventureeditor.util;

import com.google.gson.Gson;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.util.Objects;

/**
 * Manages the "dirty" state of a data object by comparing its current state
 * against an initial "snapshot." This allows for a more robust way to detect
 * meaningful changes in the UI.
 */
public class DirtyStateManager {

    private RoomData snapshot;
    private final Gson gson;

    /**
     * Constructs a new DirtyStateManager.
     */
    public DirtyStateManager() {
        this.snapshot = null;
        // Gson is thread-safe, so creating one instance for the life of this manager is efficient.
        this.gson = new Gson();
    }

    /**
     * Takes a snapshot of the provided RoomData object. This is done by creating a
     * deep copy using JSON serialization, ensuring the snapshot is completely
     * independent of the original object.
     *
     * @param roomData The room data to use as the "clean" state.
     */
    public void takeSnapshot(RoomData roomData) {
        if (roomData == null) {
            this.snapshot = null;
            return;
        }
        // A simple and effective way to deep copy the object is to serialize and then deserialize it.
        String jsonCopy = gson.toJson(roomData);
        this.snapshot = gson.fromJson(jsonCopy, RoomData.class);
    }

    /**
     * Compares the provided "current" room data against the stored snapshot to
     * determine if there are any differences by comparing their JSON representations.
     *
     * @param currentRoomData The current state of the room data from the UI.
     * @return true if the current data is different from the snapshot, false otherwise.
     */
    public boolean isDirty(RoomData currentRoomData) {
        // If there's no snapshot, the data can't be dirty relative to it.
        if (snapshot == null) {
            return true; // false;
        }

        // Convert both the snapshot and the current object to JSON strings.
        String snapshotJson = gson.toJson(snapshot);
        String currentJson = gson.toJson(currentRoomData);

        // If the JSON strings are not equal, the object is dirty.
        return !Objects.equals(snapshotJson, currentJson);
    }

    /**
     * Resets the manager by clearing the current snapshot.
     */
    public void reset() {
        this.snapshot = null;
    }
}