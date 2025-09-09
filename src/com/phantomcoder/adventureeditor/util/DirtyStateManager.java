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

    public DirtyStateManager() {
        this.snapshot = null;
        this.gson = new Gson();
    }

    public void takeSnapshot(RoomData roomData) {
        if (roomData == null) {
            this.snapshot = null;
            return;
        }
        String jsonCopy = gson.toJson(roomData);
        this.snapshot = gson.fromJson(jsonCopy, RoomData.class);
    }

    /**
     * Compares the provided "current" room data against the stored snapshot to
     * determine if there are any differences using a deep object comparison.
     *
     * @param currentRoomData The current state of the room data from the UI.
     * @return true if the current data is different from the snapshot, false otherwise.
     */
    public boolean isDirty(RoomData currentRoomData) {
        if (snapshot == null) {
            // If there's no snapshot, but there is current data, it's considered dirty.
            return currentRoomData != null;
        }
        // Use the new, robust equals method for an accurate comparison.
        return !snapshot.equals(currentRoomData);
    }

    public void reset() {
        this.snapshot = null;
    }
}